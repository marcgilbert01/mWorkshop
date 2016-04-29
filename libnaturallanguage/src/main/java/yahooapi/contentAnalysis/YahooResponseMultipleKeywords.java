package yahooapi.contentAnalysis;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gilbertm on 08/03/2016.
 */
public class YahooResponseMultipleKeywords {

    public Query query;

    public class Query {
        public Integer count;
        public String created;
        public String lang;
        public Results results;
    }

    public class Results {
        public Entities entities;
    }

    public class Entities {
        public List<Entity> entity = new ArrayList<Entity>();
    }

    public class Entity {
        public String score;
        public Text text;
        public String wikiUrl;
    }

    public class Text {

        public String start;
        public String end;
        public String endchar;
        public String startchar;
        public String content;
    }

}
