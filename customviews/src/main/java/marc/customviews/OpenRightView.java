package marc.customviews;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;

/**
 * Created by gilbertm on 25/02/2016.
 */
public class OpenRightView extends FrameLayout implements OpenRightViewHolder.OnOpenRightViewClickedListener {

    AppCompatActivity activity;
    OpenRightRecyclerView recyclerViewOpenRight;
    OpenRightAdapter openRightAdapter;
    FrameLayout frameLayoutFloating;
    Handler handler;

    // LIVE VARIABLES
    View openedItemView;
    ViewGroup openItemViewGroup;
    float itemViewHeight;
    float itemViewY;
    float fragmentOrgY;
    Fragment openedFragment;

    Long animDuration = 600L;
    final long durationMoveRight = (long) ((double)animDuration*(1D/3D));
    final long durationFlip = (long) ((double)animDuration*(2D/3D));


    public OpenRightView(Context context, AttributeSet attrs) throws Exception {
        super(context, attrs);
        if( context instanceof AppCompatActivity) {
            this.activity = (AppCompatActivity) context;
            init();
        }
        else{
            throw new Exception("OpenRightView must be used with an AppCompatActivity !");
        }
    }


    private void init() {

        handler = new Handler();
        inflate( this.activity, R.layout.open_right, this);
        recyclerViewOpenRight = (OpenRightRecyclerView) this.findViewById(R.id.recyclerViewOpenRight);
        recyclerViewOpenRight.setLayoutManager(new LinearLayoutManager(activity));
        frameLayoutFloating = (FrameLayout) this.findViewById(R.id.frameLayoutFloating);

    }


    public void setAdapter(OpenRightAdapter openRightAdapter){

        openRightAdapter.setOpenRightView(this);
        this.openRightAdapter = openRightAdapter;
        recyclerViewOpenRight.setAdapter(openRightAdapter);

    }


    @Override
    public void onOpenRightViewClicked( ViewGroup viewGroup , final int position) {

        slideItemViewToOutOfRecyclerView(viewGroup, position);

    }




    private void slideItemViewToOutOfRecyclerView(final ViewGroup viewGroup, final int position){

        final View view = viewGroup.getChildAt(0);

        if( view !=null ){

            openItemViewGroup = viewGroup;
            openedItemView = view;

            // STOP RECYCLERVIEW FROM SCROLLING
            recyclerViewOpenRight.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
            recyclerViewOpenRight.setOnInterceptTouchListener(new OpenRightRecyclerView.OnInterceptTouchListener() {
                @Override
                public boolean onInterceptTouchEvent(MotionEvent e) {
                    return true;
                }
            });

            // REMOVE VIEW FROM FRAME LAYOUT IN RECYCLERVIEW
            int width = viewGroup.getWidth();
            int height = viewGroup.getHeight();
            viewGroup.removeView(view);
            // ADD BLANK VIEW OF THE SAME SIZE INSTEAD
            View blankView = new View(activity);
            blankView.setLayoutParams(new ViewGroup.LayoutParams(width, height));
            viewGroup.addView(blankView);
            // ADD VIEW TO FLOATING LAYOUT AND POSITION ON TOP OF INVISIBLE VIEW
            frameLayoutFloating.removeAllViews();
            frameLayoutFloating.addView(view);
            frameLayoutFloating.setLayoutParams(new LayoutParams(width, height));
            frameLayoutFloating.setX(recyclerViewOpenRight.getX());
            frameLayoutFloating.setY(recyclerViewOpenRight.getY() + viewGroup.getY());
            frameLayoutFloating.setVisibility(VISIBLE);

            // MOVE FLOATING FRAME LAYOUT HALF WAY
/*
            final ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(frameLayoutFloating, "X", recyclerViewOpenRight.getX(),  new Float( recyclerViewOpenRight.getX() + recyclerViewOpenRight.getWidth() / 100*50 )  );
            objectAnimator.setDuration( durationMoveRight / 2  );
            objectAnimator.setInterpolator(new LinearInterpolator());
            objectAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {

                    ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(frameLayoutFloating, "X", frameLayoutFloating.getX(), recyclerViewOpenRight.getX()  );
                    objectAnimator1.setDuration( durationMoveRight / 2 );
                    objectAnimator1.setInterpolator(new AccelerateInterpolator());
                    objectAnimator1.start();

                    // MOVE RECYCLER VIEW
                    ObjectAnimator objectAnimatorRc = ObjectAnimator.ofFloat(recyclerViewOpenRight, "X", recyclerViewOpenRight.getX(), -recyclerViewOpenRight.getWidth());
                    objectAnimatorRc.setDuration( durationMoveRight / 2);
                    objectAnimatorRc.setInterpolator(new AccelerateInterpolator());
                    objectAnimatorRc.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            replaceItemViewWithFragment(position);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {
                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {
                        }
                    });
                    objectAnimatorRc.start();

                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
            objectAnimator.start();
*/
            // MOVE RECYCLER VIEW
            ObjectAnimator objectAnimatorRc = ObjectAnimator.ofFloat(recyclerViewOpenRight, "X", recyclerViewOpenRight.getX(), -recyclerViewOpenRight.getWidth());
            objectAnimatorRc.setDuration( durationMoveRight);
            objectAnimatorRc.setInterpolator(new AccelerateInterpolator());
            objectAnimatorRc.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    replaceItemViewWithFragment(position);
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
            objectAnimatorRc.start();

        }

    }



    private void replaceItemViewWithFragment(final int position ){

        // REMOVE ITEM VIEW FROM FLOATING FRAMELAYOUT
        final ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(frameLayoutFloating, "rotationX", 360F, 270F);
        objectAnimator.setDuration( durationFlip/2 );
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {

                if ( activity != null && !activity.isChangingConfigurations() && !activity.isFinishing() ) {

                    // REPLACE VIEW WITH FRAGMENT
                    frameLayoutFloating.removeAllViews();
                    openedFragment = openRightAdapter.getFragment(position);
                    FragmentManager fragmentManager = activity.getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.add(R.id.frameLayoutFloating, openedFragment);
                    fragmentTransaction.commit();

                    // ADJUST POSITION ACCORDING TO NEW HEIGHT
                    itemViewHeight = frameLayoutFloating.getHeight();
                    itemViewY = frameLayoutFloating.getY();
                    LayoutParams layoutParams = new LayoutParams(recyclerViewOpenRight.getWidth(), recyclerViewOpenRight.getHeight());
                    frameLayoutFloating.setLayoutParams(layoutParams);
                    fragmentOrgY = itemViewY - (recyclerViewOpenRight.getHeight() / 2 - itemViewHeight / 2);
                    frameLayoutFloating.setY(fragmentOrgY);

                    // MOVE FRAME LAYOUT BACK TO 360 DEGREES WITH ANIM
                    ObjectAnimator objectAnimatorFlipFragment = ObjectAnimator.ofFloat(frameLayoutFloating, "rotationX", 90F, 0F);
                    objectAnimatorFlipFragment.setDuration(durationFlip / 2);
                    objectAnimatorFlipFragment.setInterpolator(new AccelerateInterpolator());
                    objectAnimatorFlipFragment.start();

                    // MOVE FRAGMENT ON TOP OF THE PARENT VIEW
                    ObjectAnimator objectAnimatorMoveFragmentToUp = ObjectAnimator.ofFloat(frameLayoutFloating, "Y", frameLayoutFloating.getY(), 0F);
                    objectAnimatorMoveFragmentToUp.setDuration(durationFlip / 2);
                    objectAnimatorMoveFragmentToUp.setInterpolator(new AccelerateInterpolator());
                    objectAnimatorMoveFragmentToUp.start();
                }

            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        objectAnimator.start();

    }




    private void replaceFragmentWithItemView(){

        if( openedItemView!=null && openItemViewGroup!=null ){

            // FLIP FRAMELAYOUT BACK TO 90 DEGREES
            ObjectAnimator objectAnimatorFlipBackFragment = ObjectAnimator.ofFloat(frameLayoutFloating, "rotationX", 0F, 90F);
            objectAnimatorFlipBackFragment.setDuration(durationFlip / 2);
            objectAnimatorFlipBackFragment.start();

            // MOVE FRAME LAYOUT BACK TO POSITION (CONSIDERING BIG HEIGHT)
            ObjectAnimator objectAnimatorMoveFragmentDown = ObjectAnimator.ofFloat(frameLayoutFloating, "Y", frameLayoutFloating.getY() , fragmentOrgY  );
            objectAnimatorMoveFragmentDown.setDuration(durationFlip / 2);
            objectAnimatorMoveFragmentDown.setInterpolator(new AccelerateInterpolator());
            objectAnimatorMoveFragmentDown.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {

                    if (activity != null && !activity.isChangingConfigurations() && !activity.isFinishing() ) {

                        // RE ADJUST HEIGHT OF FRAMELAYOUT FOR ITEM VIEW
                        LayoutParams layoutParams = new LayoutParams(recyclerViewOpenRight.getWidth(), (int) itemViewHeight);
                        frameLayoutFloating.setLayoutParams(layoutParams);
                        frameLayoutFloating.setY(itemViewY);
                        // REMOVE FRAGMENT FROM FRAME LAYOUT
                        FragmentManager fragmentManager = activity.getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.remove(openedFragment);
                        fragmentTransaction.commit();
                        frameLayoutFloating.removeAllViews();
                        frameLayoutFloating.addView(openedItemView);
                        // COMPLETE FRAMELAYOUT ROTATION TO SHOW ITEMVIEW
                        ObjectAnimator objectAnimatorItemViewRotateBack = ObjectAnimator.ofFloat(frameLayoutFloating, "rotationX", 270F, 360F);
                        objectAnimatorItemViewRotateBack.setDuration(durationFlip / 2);
                        objectAnimatorItemViewRotateBack.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {
                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                // SLIDE ITEM VIEW BACK TO RECYCLER VIEW
                                slideItemViewBackToRecyclerView();
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {
                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {
                            }
                        });
                        objectAnimatorItemViewRotateBack.start();
                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
            objectAnimatorMoveFragmentDown.start();
        }
    }


    public void slideItemViewBackToRecyclerView(){

        // MOVE RECYCLER VIEW BACK TO SCREEN
        ObjectAnimator objectAnimatorRcBackToScreen = ObjectAnimator.ofFloat(recyclerViewOpenRight, "X", -recyclerViewOpenRight.getWidth() , 0F);
        objectAnimatorRcBackToScreen.setDuration( durationMoveRight);
        objectAnimatorRcBackToScreen.setInterpolator(new AccelerateInterpolator());
        objectAnimatorRcBackToScreen.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {

                // PUT ITEM VIEW BACK IN RECYCLERVIEW
                frameLayoutFloating.removeAllViews();
                frameLayoutFloating.setVisibility(GONE);
                openItemViewGroup.removeAllViews();
                openItemViewGroup.addView(openedItemView);
                // MAKE RECYCLERVIEW SCROLLABLE AGAIN
                recyclerViewOpenRight.setOnTouchListener(new OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return false;
                    }
                });
                recyclerViewOpenRight.setOnInterceptTouchListener(null);

            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        objectAnimatorRcBackToScreen.start();


    }



    public void closeFragment() {

        replaceFragmentWithItemView();
    }

    static abstract public class OpenRightAdapter<VH extends OpenRightViewHolder> extends RecyclerView.Adapter{

        OpenRightView openRightView;

        public void setOpenRightView(OpenRightView openRightView) {
            this.openRightView = openRightView;
        }

        public abstract Fragment getFragment(int position);

        public abstract VH onCreateOpenRightViewHolder(ViewGroup parent , int viewType);

        public abstract void onBindOpenRightViewHolder( VH viewHolder, int position );

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            OpenRightViewHolder openRightViewHolder = onCreateOpenRightViewHolder(parent, viewType);
            openRightViewHolder.setOnFrameLayoutClickedListener(openRightView);

            return  openRightViewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            VH vh = (VH) holder;
            vh.setPosition(position);
            onBindOpenRightViewHolder(vh, position);
        }

    }



}
