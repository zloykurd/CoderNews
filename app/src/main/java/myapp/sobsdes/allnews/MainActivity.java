package myapp.sobsdes.allnews;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.os.AsyncTask;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private onbuttonclickHttpPost mTask = null;
    int counter =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        downloadPage();
    }

    private void downloadPage() {

        String csMobile = Context.CONNECTIVITY_SERVICE;
        ConnectivityManager cmMobile = (ConnectivityManager) getSystemService(csMobile);
        cmMobile.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        String csWifi = Context.CONNECTIVITY_SERVICE;
        ConnectivityManager cmWifi = (ConnectivityManager) getSystemService(csWifi);
        cmWifi.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (cmMobile.getActiveNetworkInfo() == null
                || cmWifi.getActiveNetworkInfo() == null) {
            Toast.makeText(
                    MainActivity.this,
                    "Отсутствует подключение к интернету. Проверьте настройки",
                    Toast.LENGTH_LONG).show();
        } else {
            if (mTask != null
                    && mTask.getStatus() != onbuttonclickHttpPost.Status.FINISHED) {
                mTask.cancel(true);
            }

            mTask = (onbuttonclickHttpPost) new onbuttonclickHttpPost().execute();

        }

    }


    /********/
    public class onbuttonclickHttpPost extends AsyncTask<String, Void, Void> {
        public class execute {
        }

        @Override
        protected void onPreExecute() {
            progressBar = (ProgressBar) findViewById(R.id.progressBar2);
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... params) {
            byte[] result = null;
            String str = "";


            HttpPost httppost24kg = new HttpPost(
                    "http://24.kg/rss/all/");
            HttpPost httppostvbkg = new HttpPost(
                    "http://www.vb.kg/?rss");
            HttpPost httppostZanoza = new HttpPost(
                    "http://zanoza.kg/?rss");
            HttpPost httppostVesti = new HttpPost(
                    "http://vesti.kg/index.php?option=com_k2&view=itemlist&format=feed");


            downloadAllNews(httppost24kg);
            downloadAllNews(httppostvbkg);
            downloadAllNews(httppostZanoza);
            downloadAllNews(httppostVesti);
            return null;
        }

        public void downloadAllNews(HttpPost hp) {
            HttpClient httpclient = new DefaultHttpClient();
            NewsDatabase db = new NewsDatabase(MainActivity.this);
            try {
                HttpResponse response = httpclient.execute(hp);
                StatusLine statusLine = response.getStatusLine();
                String responseStr = EntityUtils.toString(response.getEntity());

                StringReader stringReader = new StringReader(responseStr);
                InputSource inputSource = new InputSource(stringReader);
                DocumentBuilderFactory dbfact = DocumentBuilderFactory
                        .newInstance();
                DocumentBuilder builder = null;

                DocumentBuilder builderCompany = null;

                try {
                    builder = dbfact.newDocumentBuilder();
                } catch (ParserConfigurationException ex) {
                    ex.printStackTrace();
                }

                Document docCompany = null;
                Document doc = null;

                try {
                    doc = (Document) builder.parse(inputSource);
                    doc.getDocumentElement().normalize();

                    NodeList nodeLstNews = doc.getElementsByTagName("item");

                    for (int s = 0; s < nodeLstNews.getLength(); s++) {

                        Node fstNode = nodeLstNews.item(s);

                        if (fstNode.getNodeType() == Node.ELEMENT_NODE) {

                            Element fstElmnt = (Element) fstNode;
                            NodeList fstNmElmntLst = fstElmnt
                                    .getElementsByTagName("title");
                            Element fstNmElmnt = (Element) fstNmElmntLst
                                    .item(0);
                            NodeList fstNm = fstNmElmnt.getChildNodes();

                            NodeList secNmElmntLst = fstElmnt
                                    .getElementsByTagName("link");
                            Element secNmElmnt = (Element) secNmElmntLst
                                    .item(0);
                            NodeList secNm = secNmElmnt.getChildNodes();

                            NodeList thrdNmElmntLst = fstElmnt
                                    .getElementsByTagName("description");
                            Element thrdNmElmnt = (Element) thrdNmElmntLst
                                    .item(0);
                            NodeList thrdNm = thrdNmElmnt.getChildNodes();


                            NodeList fvNmElmntLst = fstElmnt
                                    .getElementsByTagName("description");
                            Element fvNmElmnt = (Element) fvNmElmntLst.item(0);
                            NodeList fvNm = fvNmElmnt.getChildNodes();


                            NodeList sixNmElmntLst = fstElmnt
                                    .getElementsByTagName("pubDate");
                            Element sixNmElmnt = (Element) sixNmElmntLst.item(0);
                            NodeList sixNm = sixNmElmnt.getChildNodes();

                            NodeList sevenNmElmntLst = fstElmnt
                                    .getElementsByTagName("guid");
                            Element sevenNmElmnt = (Element) sevenNmElmntLst.item(0);
                            NodeList sevenNm = sevenNmElmnt.getChildNodes();
                            String t;

                            if (((Node) fstNm.item(0))
                                    .getNodeValue() != null) {
                                t = ((Node) fstNm.item(0))
                                        .getNodeValue().toString();
                            } else {
                                t = "";
                            }
//ДЗ
//if == 1..2..3..44..
                            if (counter ==1){
                                News newspro = new News(
                                        t,
                                        ((Node) sixNm.item(0)).getNodeValue()
                                                .toString(),
                                        "",//изображение
                                        "",//desc
                                        "",//категория
                                        "24.kg"//source
                                );
                                Log.d("MainAct", newspro.toString());
                                db.addNews(newspro); // добавить счетчик
                            }
                            if (counter ==2){
                                News newspro = new News(
                                        t,
                                        ((Node) sixNm.item(0)).getNodeValue()
                                                .toString(),
                                        "",//изображение
                                        "",//desc
                                        "",//категория
                                        "vb.kg"//source
                                );
                                Log.d("MainAct", newspro.toString());
                                db.addNews(newspro); // добавить счетчик
                            }
                            if (counter ==3){
                                News newspro = new News(
                                        t,
                                        ((Node) sixNm.item(0)).getNodeValue()
                                                .toString(),
                                        "",//изображение
                                        "",//desc
                                        "",//категория
                                        "zanoza.kg"//source
                                );
                                Log.d("MainAct", newspro.toString());
                                db.addNews(newspro); // добавить счетчик
                            }

                            if (counter ==4){
                                News newspro = new News(
                                        t,
                                        ((Node) sixNm.item(0)).getNodeValue()
                                                .toString(),
                                        "",//изображение
                                        "",//desc
                                        "",//категория
                                        "vesti.kg"//source
                                );
                                Log.d("MainAct", newspro.toString());
                                db.addNews(newspro); // добавить счетчик
                            }



                        }

                    }

                } catch (SAXException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            } catch (ClientProtocolException e) {
            } catch (IOException e) {
            }
            counter++;
        }

        /**
         * on getting result
         */
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            progressBar.setProgress(0);

            Intent i = new Intent(MainActivity.this, NewsActivity.class);
            startActivity(i);
            finish();


        }
    }


}
