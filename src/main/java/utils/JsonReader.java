package utils;

import com.aventstack.extentreports.Status;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//import com.sun.tools.sjavac.Log;

public class JsonReader {
    public static final String JKS = "JKS";
    private static final String[] certs = {"aem-buyflow-prod.corp.chartercom.cer", "Charter Communications Issuing CA1.cer", "aem-buyflow-prod.corp.chartercom1.cer"};
    private static final String trustStorePath = System.getProperty("java.io.tmpdir") + File.separator + "test.keystore";
    private static final char[] password = "123456".toCharArray();

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    public static Map<String, String> parse(JSONObject json, Map<String, String> out) throws JSONException {
        Iterator<String> keys = json.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            String val = null;
            if (json.getJSONObject(key) instanceof JSONObject) {
                JSONObject value = json.getJSONObject(key);
                parse(value, out);
            } else {
                val = json.getString(key);
            }


            if (val != null) {
                out.put(key, val);
            }
        }
        return out;
    }

    public static JSONObject getJSONObject(String url) throws IOException, JSONException, KeyStoreException, CertificateException, NoSuchAlgorithmException {
        KeyStore keyStore = KeyStore.getInstance(JKS);
        keyStore.load(null, null);
        for (String certFile : certs) {
            try (FileInputStream fileInputStream = new FileInputStream(certFile);
                 BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream)
            ) {
                while (bufferedInputStream.available() > 0) {
                    Certificate certificate = CertificateFactory.getInstance("X.509").generateCertificate(bufferedInputStream);
                    keyStore.setCertificateEntry(certFile, certificate);
                }
            }
        }
        keyStore.store(new FileOutputStream(trustStorePath), password);

        System.setProperty("javax.net.ssl.trustStore", trustStorePath);
        System.setProperty("javax.net.ssl.trustStorePassword", new String(password));
        System.setProperty("javax.net.ssl.trustStoreType", JKS);

        JSONObject json = readJsonFromUrl(url);
/*        URL url = new URL("http://example.com/json");
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(new InputStreamReader(url.openStream()));*/
        ExtentReport.getExtentTest().log(Status.INFO, "Fetched JSON from given URL");
        return json;
    }

    public static HashMap getRequiredJSONNodeValues(JSONObject json, String reqNodeName) throws IOException {
        HashMap nodevalues = new HashMap();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(String.valueOf(json));
        List<Model> models = new TreeModelParser().traverse(rootNode);
        //  JSONObject location = (JSONObject) resultObject.get("location");
        int i = 0;
        for (Model mod : models) {
            //   System.out.println(mod + " => Parent: " + mod.getParent());
            String nodeName = mod.getNodeName();
            if (nodeName.equalsIgnoreCase(":children")) {
                System.out.println(nodeName + " : " + mod.getNodeValue());
                //   nodevalues.put(nodeName + i, mod.getNodeValue());
                //    i++;
            }
        }
        if (!nodevalues.isEmpty()) {
            ExtentReport.getExtentTest().log(Status.INFO, "Retried json node values");
        }
        return nodevalues;
    }

    public static void writeJSONToFile(JSONObject json, String fileName) {
        try {
            String directoryName = System.getProperty("user.dir") + "/ExtentReport/JSONFiles/";
            //  String directoryName = PATH.concat(this.getClassName());

            File directory = new File(directoryName);
            if (!directory.exists()) {
                directory.mkdir();
                // If you require it to make the entire directory path including parents,
                // use directory.mkdirs(); here instead.
            }
           /* Files.createDirectories(Paths.get(directoryName));
            String jsonFolderPath = System.getProperty("user.dir") + "/ExtentReport/JSONFiles/";*/
            // Writing to a file
            File file = new File(directoryName + fileName + ".json");
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(file);
            System.out.println("Writing JSON object to file");
            System.out.println("-----------------------");
            //     System.out.print(json);

            fileWriter.write(String.valueOf(json));
            fileWriter.flush();
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Finds the difference between two HashMaps a and b.
     * Returns a HashMap containing elements in b that are not in a
     *
     * @return the difference as a HashMap
     */
    public static String checkReqValuesInMap(HashMap a, String[] b) {
        //   Iterator bKeyIterator = b.keySet().iterator();
        Iterator aKeyIterator = a.keySet().iterator();
        Object key;
        String k = null;


        for (int i = 0; i < b.length; i++) {
            while (aKeyIterator.hasNext()) {
                key = aKeyIterator.next();
                //   if (!a.containsKey(key)) {
                Object valueA = a.get(key);
                System.out.println(valueA);
                if (valueA != null) {
                    if (!valueA.toString().contains(b[i])) {
                        String s = "Required " + b[i] + " found in path";
                        //  System.out.println("S value  " + s);
                        k = s + " :: " + k;
                        break;
                    } else {
                        String s = "Required " + b[i] + " not found in path";
                        //  System.out.println("S value  " + s);
                        k = s + " :: " + k;
                    }
                }
                // System.out.println("K value  " + k);
            }
        }

        return k;
    }

    public static Object getReqJsonObject(JSONObject jsonObj, String reqNode) {
        String keyStr;
        Object keyvalue = null;
        for (Object key : jsonObj.keySet()) {
            //based on you key types
            keyStr = (String) key;
            if (keyStr.equalsIgnoreCase(reqNode)) {
                keyvalue = jsonObj.get(keyStr);
                //Print key and value
                //     System.out.println("key: " + keyStr + " value: " + keyvalue);

                //for nested objects iteration if required
               /* if (keyvalue instanceof JSONObject)
                    printJsonObject((JSONObject) keyvalue);*/
                break;
            }
        }
        return keyvalue;

    }

    public static HashMap getJSONValues(JSONObject jsonObj, String reqVal) {
        HashMap map = new HashMap();
        String keyStr;
        Object keyvalue = null;
        int i = 1;
        for (Object key : jsonObj.keySet()) {
            //based on you key types
            keyStr = (String) key;
            keyvalue = jsonObj.get(keyStr);
            //Print key and value
            //   System.out.println("key: " + keyStr + " value: " + keyvalue);
            if (reqVal.equalsIgnoreCase("key")) {
                map.put("key" + i, keyStr);
            } else if (reqVal.equalsIgnoreCase("value")) {
                map.put("value" + i, keyvalue);
            }
            i++;
        }
        return map;

    }

  /*  public static void RestApi() {
        String CookieValue;
        int statusCode = 0;
        StringBuilder errorid = new StringBuilder();
        RestAssured.useRelaxedHTTPSValidation();
        String env = "PROD";
        if (env.contains("QA")) {
            //	CookieValue="DEDALOOSAPI=dedaloos_api14_ncw";
            CookieValue = "_abck=AC270A721B6FDC283C692D7BF4C008CE~-1~YAAQjDkgF4mTuaCHAQAAHc3NoQnjoePd19jdCBLu8gJ6mj+juE7HHffKHfg+IuovKPgARMJQvonAKR67Xmc3Hc2mcGVmYaI4xAjUT/3UIWd69Qo5S9QY/EX8gXFhvcJqtASC5vDXKXoBvFtvBXT7zC4wf4nfsWXPSKEXDzLki2oY2Yt4eqFQzAIIilhWLEsOTe26C+ZkbUEEK57yK23DTvVqGRjVWq0lpYD+pCIA/Nq1rlQhGTWtWb4/LLubN9jlN9hNpsgc3x38gbpzxr2sG6Ng0qmye1Mhbor1SP4BUuROKHw2W1/6Zt3f6P/jxQWk37pYWxRXypfoKrz61nLnRAwUc9T3Ce9gg3rUMVWc0ggXRKx3iCbhHZx1VSixLK5geX7/EM9kfOX2~-1~-1~-1; ak_bmsc=E88DDEC2BFDF84C8F86B995059B239A5~000000000000000000000000000000~YAAQjDkgF4qTuaCHAQAAHc3NoRPdqpvwVh0yb2nHIM+VTaSnbZf3GyYKvO4iIFZCUSmWToU8mAwy/KdAyUPuQwE85VcSW353PCmpozuvhtrMQTIiiMpDXWXf3u9OXa7XgUZTAz71u8+YaJwO8xCDRV1plCp6nLTKvOKMlDsHTi0nqR/1TQzQfY2Mj65SMflCx93ngfe/rryonzKoYGc5JrStqrG7kD0qejxqUBCmI44LIOWZsALPbPJSJ/qcaLHR17SGqjBoYidMxZbq9b2SAlhel56GmWuNx/afAZA18Xim4uW7EsWxIAk3WOvN61jbkg2VBKROc/Q3Dfk+I1SARlBpeumUrMyCizfSDRD1Dp8jU8clc34FvurCON7cnsKU; bm_sv=9F2939618C0C4A181331CB8992AF8729~YAAQjDkgF9OauaCHAQAAWZDSoRNFgjDBx1Mv4MbdLOd61Emr2Z8OhURfcR+spMv8CBFev0vHWU1Cs0k0md3quVE7SgXm9M6lXlgdQnLA94mX1JpzdvyBu9Lmoo87WnS8PVicKAe++/Ivf/nAwFV2b2+xiBXh8ozc/EGWiAZPqCvLiEre5uRapG7Q9f26ZRlpvRjsVUgCZy6s+ebLVBH2ItlUiQmCiBkES5X82gTYGTJF+MaGt+Y7iaV1v3OQmVtyqZY=~1; bm_sz=6E552FE97757FF6FA8505F5721724BB3~YAAQjDkgF4uTuaCHAQAAHc3NoRMVW+eRFa5MSjLMGHdENsB6nZ8R4dJSIwfhX6/RhriakodkbcGMb+7Lqcr1xE6qcL0G64+ci56utCGk57n8i5Ygolxy7PJ1lvsWwRe9SH15+TrbXqMIbs8WGqMwzhms3cisFDQ20DfZuJzgxngnYbKmqVdwo3QPPxUM8+Op7V7RPnGsdd6M8pWmpFZnB+5vNdhADUQtdr+yg5Mkj3PrnVXL6UkhjiPLJNB1oEjxzOFN8qzos2UcEYHzJdXHv7xIgvnbv/QlmVfBOP1/oE5BiOyAQg==~3425347~3420467; DEDALOOSAPI=dedaloos_api15_ncw; SERVERID=disp1nce_resi65; akaas_AB-Testing=2147483647~rv=80~id=8ee67edd61c4ae7356712d2d6b4ef84e; akavpau_Global=1682047444~id=969d6cb7a68354914dc043b9367b03d4";

            //CookieValue = "DEDALOOSAPI=uatdedaloosapiserverncw21";
        } else {
            CookieValue = "_abck=AC270A721B6FDC283C692D7BF4C008CE~-1~YAAQjDkgF4mTuaCHAQAAHc3NoQnjoePd19jdCBLu8gJ6mj+juE7HHffKHfg+IuovKPgARMJQvonAKR67Xmc3Hc2mcGVmYaI4xAjUT/3UIWd69Qo5S9QY/EX8gXFhvcJqtASC5vDXKXoBvFtvBXT7zC4wf4nfsWXPSKEXDzLki2oY2Yt4eqFQzAIIilhWLEsOTe26C+ZkbUEEK57yK23DTvVqGRjVWq0lpYD+pCIA/Nq1rlQhGTWtWb4/LLubN9jlN9hNpsgc3x38gbpzxr2sG6Ng0qmye1Mhbor1SP4BUuROKHw2W1/6Zt3f6P/jxQWk37pYWxRXypfoKrz61nLnRAwUc9T3Ce9gg3rUMVWc0ggXRKx3iCbhHZx1VSixLK5geX7/EM9kfOX2~-1~-1~-1; ak_bmsc=E88DDEC2BFDF84C8F86B995059B239A5~000000000000000000000000000000~YAAQjDkgF4qTuaCHAQAAHc3NoRPdqpvwVh0yb2nHIM+VTaSnbZf3GyYKvO4iIFZCUSmWToU8mAwy/KdAyUPuQwE85VcSW353PCmpozuvhtrMQTIiiMpDXWXf3u9OXa7XgUZTAz71u8+YaJwO8xCDRV1plCp6nLTKvOKMlDsHTi0nqR/1TQzQfY2Mj65SMflCx93ngfe/rryonzKoYGc5JrStqrG7kD0qejxqUBCmI44LIOWZsALPbPJSJ/qcaLHR17SGqjBoYidMxZbq9b2SAlhel56GmWuNx/afAZA18Xim4uW7EsWxIAk3WOvN61jbkg2VBKROc/Q3Dfk+I1SARlBpeumUrMyCizfSDRD1Dp8jU8clc34FvurCON7cnsKU; bm_sv=9F2939618C0C4A181331CB8992AF8729~YAAQjDkgF9OauaCHAQAAWZDSoRNFgjDBx1Mv4MbdLOd61Emr2Z8OhURfcR+spMv8CBFev0vHWU1Cs0k0md3quVE7SgXm9M6lXlgdQnLA94mX1JpzdvyBu9Lmoo87WnS8PVicKAe++/Ivf/nAwFV2b2+xiBXh8ozc/EGWiAZPqCvLiEre5uRapG7Q9f26ZRlpvRjsVUgCZy6s+ebLVBH2ItlUiQmCiBkES5X82gTYGTJF+MaGt+Y7iaV1v3OQmVtyqZY=~1; bm_sz=6E552FE97757FF6FA8505F5721724BB3~YAAQjDkgF4uTuaCHAQAAHc3NoRMVW+eRFa5MSjLMGHdENsB6nZ8R4dJSIwfhX6/RhriakodkbcGMb+7Lqcr1xE6qcL0G64+ci56utCGk57n8i5Ygolxy7PJ1lvsWwRe9SH15+TrbXqMIbs8WGqMwzhms3cisFDQ20DfZuJzgxngnYbKmqVdwo3QPPxUM8+Op7V7RPnGsdd6M8pWmpFZnB+5vNdhADUQtdr+yg5Mkj3PrnVXL6UkhjiPLJNB1oEjxzOFN8qzos2UcEYHzJdXHv7xIgvnbv/QlmVfBOP1/oE5BiOyAQg==~3425347~3420467; DEDALOOSAPI=dedaloos_api15_ncw; SERVERID=disp1nce_resi65; akaas_AB-Testing=2147483647~rv=80~id=8ee67edd61c4ae7356712d2d6b4ef84e; akavpau_Global=1682047444~id=969d6cb7a68354914dc043b9367b03d4";
        }

        RestAssured.baseURI = "https://directsales-disp2-prod.nce.corp.chartercom.com/content/spectrum/spectrum-serviceability/en.model.json";
        try {
            RequestSpecification httpRequest = RestAssured.given();
            httpRequest.header("client-id", " SRG-TEST");
            httpRequest.header("Content-Type", "application/json");
            httpRequest.header("Cookie", "CookieValue");
          *//*  httpRequest.queryParam("salesChannel", salesChannel);
            httpRequest.queryParam("affiliateId", affiliateId);*//*
            //	httpRequest.body("{\"addressInformation\":{\"line1\":\"303 W Young Ave\",\"line2\":\"Apt 6\",\"postalCode\":\"64093\"},\"channelInformation\":{\"affiliateId\":\"218746\",\"channel\":\"RESI-COM\",\"customerPresent\":true,\"v\":null,\"originatingFlowId\":null,\"retailTransId\":null},\"fetchOffers\":true,\"offerType\":\"R\"}");
            Response response = httpRequest.request(Method.GET);
            statusCode = response.getStatusCode();
            System.out.println("Status code: " + statusCode);
            String responseBody = response.getBody().asString();
            //  writeToJson(TestName, "Tfn", responseBody);
            System.out.println("Response Body is :=>  " + responseBody);
            //   Assert.assertEquals(statusCode, 200);
            int size = errorid.length();
            errorid.delete(0, size);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(responseBody);
            JsonNode phonesNode = rootNode.get("phones");
            //    System.out.println("Response : "+ phonesNode);
            // String localization = getResponseFromNode(phonesNode, "localization", path, "TFN", "Localization", element);
            String actualResponse = null;
            List<String> tfnList
                    = Arrays.asList("localization", "enter_your_info", "localization_error", "customization", "error", "prepayment", "ssn", "house_not_found_gis_color_red", "localization_error_non_pay", "address_clarification_move", "confirmation_automated", "confirmation_non_automated_mobile_accepted", "offer_mobile_banner", "multiple_address", "bulk_rejection", "errorBuyFlowError", "review_order", "localization_usps_invalid", "confirmation_automated_mobile_accepted", "address_clarification", "schedule", "bulk_before_submit", "localization_cust_moving", "bulk_after_submit", "localization_dotnet_ebf", "catastrophic_error", "address_not_match", "confirmation_non_automated", "localization_misc_error", "rdof", "storefront");
            HashMap actualData = new HashMap<String, String>();
            HashMap expectedData = new HashMap<String, String>();
            // For Each Loop for iterating ArrayList
            for (String tfn : tfnList) {
                JsonNode node = phonesNode.get(tfn);
                if (!node.equals(null) || !node.isMissingNode() || node != null) {
                    actualResponse = node.toString().replaceAll("\"", "");
                    System.out.println("Actual node value of - " + tfn + " is - " + actualResponse);
                    actualData.put(tfn.trim(), actualResponse.trim());

                }

            }

        } catch (NullPointerException | IOException e) {
            //  Log.info(e.getMessage());
        }
    }*/

}