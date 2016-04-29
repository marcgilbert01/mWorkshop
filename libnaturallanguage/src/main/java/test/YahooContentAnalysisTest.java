package test;

import junit.framework.TestCase;

import org.junit.Test;

import yahooapi.contentAnalysis.YahooContentAnalysisApi;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertNotNull;

/**
 * Created by gilbertm on 08/03/2016.
 */
public class YahooContentAnalysisTest {

    @Test
    public void testGetKeywords(){

        YahooContentAnalysisApi yahooContentAnalysis = new YahooContentAnalysisApi();


        String text = "The moment the rodent said his last squeaks was immortalised by freelance photographer Gabor Wachter who had left his home in Birmingham for a day out with his girlfriend. ";

        String[] keywords = yahooContentAnalysis.getKeywords( text );
        assertNotNull(keywords);
        assertTrue( keywords.length==1 );
        assertEquals( "Gabor Wachter" , keywords[0] );


        text = "A senior coroner has called for a national review of police use of restraints after an inquest jury found a decision to restrain a 57-year-old man suffering from lung cancer and pneumonia in hospital contributed to his death.\n" +
                "\n" +
                "The verdict follows a four-year battle for justice by the family of Philmore Mills, who died in hospital in 2011. Following a four-week inquest at Reading coroner’s court, the jury ruled that a police decision to take the seriously ill father to the ground and handcuff him with his face to the floor was a contributory factor in his death moments later.\n" +
                "\n" +
                "Mills’ death was caused by “cardiorespiratory collapse due to hypoxia [shortage of oxygen to the brain] due to the severe lung and heart disease in association with restraint” used by officers, the jury found.\n" +
                "\n" +
                "Mills had been admitted to Wexham Park hospital on 21 December 2011with a chest infection and an irregular heartbeat. His condition worsened over the course of a week and on Boxing Day, when his family paid a visit, Mills was wearing an oxygen mask. ";


        keywords = yahooContentAnalysis.getKeywords( text );
        assertNotNull(keywords);
        assertTrue( keywords.length>0 );
        Boolean keywordFound = false;
        for( String keyword : keywords  ){
            if( keyword.contains("Philmore Mills") ){
                keywordFound = true;
            }
        }
        TestCase.assertTrue(keywordFound);

        keywords = yahooContentAnalysis.getKeywords("nothing in there");

        assertNull(keywords);

    }

    @Test
    public void testGetKeywordsFromUrl(){

        YahooContentAnalysisApi yahooContentAnalysis = new YahooContentAnalysisApi();
        // String[] keywords = yahooContentAnalysis.getKeywordsFromUrl("http://www.theguardian.com/uk-news/2016/feb/09/police-restrained-philmore-mills-death-wexham-park-hospital-inquest");
        String[] keywords = yahooContentAnalysis.getKeywordsFromUrl("http://www.theguardian.com/business/2016/apr/13/boots-staff-under-pressure-to-milk-the-nhs-says-pharmacists-union");



        assertNotNull(keywords);
        assertTrue( keywords.length>0 );
        Boolean keywordFound = false;
        for( String keyword : keywords  ){
            if( keyword.contains("Philmore Mills") ){
                keywordFound = true;
            }
        }
        TestCase.assertTrue(keywordFound);

        keywords = yahooContentAnalysis.getKeywords("nothing in there");

        assertNull(keywords);

    }





}
