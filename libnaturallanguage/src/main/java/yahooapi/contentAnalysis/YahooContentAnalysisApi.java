package yahooapi.contentAnalysis;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gilbertm on 08/03/2016.
 */





public class YahooContentAnalysisApi {

    final static String API_URL = "https://query.yahooapis.com/v1/public/yql";


    public String[] getKeywords(String text){

        String[] keywords = null;

        try {
            String responseStr = sendQueryToYahoo("select * from contentanalysis.analyze where text='" + text + "'");
            YahooResponse yahooResponse = new Gson().fromJson(responseStr,YahooResponse.class);
            if( yahooResponse!=null &&
                yahooResponse.query!=null &&
                yahooResponse.query.results!=null &&
                yahooResponse.query.results.entities!=null &&
                yahooResponse.query.results.entities.entity!=null
              ) {

                List<YahooResponse.Entity> entityList = yahooResponse.query.results.entities.entity;
                keywords = new String[entityList.size()];
                for (int e = 0; e < entityList.size(); e++) {
                    keywords[e] = entityList.get(e).text.content;
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return keywords;

    }


    public String[] getKeywordsFromUrl(String url){

        String[] keywords = null;

        try {
            String responseStr = sendQueryToYahoo("select * from contentanalysis.analyze where url='" + url + "'");
            YahooResponse yahooResponse = new Gson().fromJson(responseStr,YahooResponse.class);
            if( yahooResponse!=null &&
                    yahooResponse.query!=null &&
                    yahooResponse.query.results!=null &&
                    yahooResponse.query.results.entities!=null &&
                    yahooResponse.query.results.entities.entity!=null
                    ) {

                List<YahooResponse.Entity> entityList = yahooResponse.query.results.entities.entity;
                keywords = new String[entityList.size()];
                for (int e = 0; e < entityList.size(); e++) {
                    keywords[e] = entityList.get(e).text.content;
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return keywords;

    }




    private String sendQueryToYahoo(String yqlQuery ) throws IOException {

        String responseStr = null;

        URL url = new URL(API_URL);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        // ADD HEADER PROPERTIES
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Host", "query.yahooapis.com");
        httpURLConnection.setRequestProperty("User-Agent", "Mozilla / 5.0 (Windows NT 6.1; WOW64; rv:44.0) Gecko/20100101 Firefox/44.0" );
        httpURLConnection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8" );
        httpURLConnection.setRequestProperty("Accept-Language", "en-GB,en;q=0.5");
        httpURLConnection.setRequestProperty("Accept-Encoding", "gzip, deflate" );
        httpURLConnection.setRequestProperty("Connection", "keep-alive" );
        // BUILD PARAMETERS
        StringBuilder stringBuilderParams = new StringBuilder();
        stringBuilderParams.append( "q=" );
        stringBuilderParams.append( URLEncoder.encode( yqlQuery , "UTF-8") );
        //stringBuilderParams.append(URLEncoder.encode("select * from contentanalysis.analyze where text='Italian sculptors and painters of the renaissance favored the Virgin Mary for inspiration'", "UTF-8"));
        stringBuilderParams.append( "&format=json" );
        stringBuilderParams.append( "&env=" );
        stringBuilderParams.append( URLEncoder.encode( "store://datatables.org/alltableswithkeys" , "UTF-8" ) );
        String urlParameters = stringBuilderParams.toString();
        // SEND POST REQUEST
        httpURLConnection.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();
        // READ RESPONSE CODE
        int responseCode = httpURLConnection.getResponseCode();
        // READ RESPONSE
        if( responseCode==200 ) {
            InputStream inputStream = httpURLConnection.getInputStream();
            StringBuilder stringBuilder = new StringBuilder();
            final byte data[] = new byte[1024];
            int count;
            while ((count = inputStream.read(data, 0, 1024)) != -1) {
                byte[] dataOk = new byte[count];
                System.arraycopy(data, 0, dataOk, 0, count);
                stringBuilder.append(new String(dataOk));
            }
            responseStr = stringBuilder.toString();
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return responseStr;
    }


}
