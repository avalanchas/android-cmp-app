package com.sourcepoint.gdpr_cmplibrary;

import com.sourcepoint.gdpr_cmplibrary.exception.GenericSDKException;
import com.sourcepoint.gdpr_cmplibrary.exception.InvalidLocalDataException;
import com.sourcepoint.gdpr_cmplibrary.exception.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.sourcepoint.gdpr_cmplibrary.CustomJsonParser.getBoolean;
import static com.sourcepoint.gdpr_cmplibrary.CustomJsonParser.getHashMap;
import static com.sourcepoint.gdpr_cmplibrary.CustomJsonParser.getJson;
import static com.sourcepoint.gdpr_cmplibrary.CustomJsonParser.getString;

public class GDPRUserConsent {
    public String uuid;
    public ArrayList<String> acceptedVendors;
    public ArrayList<String> acceptedCategories;
    public ArrayList<String> specialFeatures;
    public ArrayList<String> legIntCategories;
    public String consentString;
    public HashMap TCData;
    public VendorGrants vendorGrants;
    public Logger logger;

    public GDPRUserConsent(Logger logger) {
        acceptedVendors = new ArrayList<>();
        acceptedCategories = new ArrayList<>();
        specialFeatures = new ArrayList<>();
        legIntCategories = new ArrayList<>();
        consentString = StoreClient.DEFAULT_EMPTY_CONSENT_STRING;
        uuid = StoreClient.DEFAULT_EMPTY_UUID;
        TCData = new HashMap();
        vendorGrants = new VendorGrants();
        this.logger = logger;
    }

    public GDPRUserConsent(JSONObject jConsent, Logger logger) throws ConsentLibException {
        this.logger = logger;
        init(jConsent);
    }

    public GDPRUserConsent(JSONObject jConsent, String uuid, Logger logger) throws ConsentLibException {
        this.logger = logger;
        init(jConsent, uuid);
    }

    private void init(JSONObject jConsent) throws ConsentLibException {
        String consentUUID;
        try {
            consentUUID = jConsent.getString("uuid");
        } catch (JSONException e) {
            logger.error(new InvalidLocalDataException(e, "No uuid found on jConsent"));
            throw new ConsentLibException(e, "No uuid found on jConsent");
        }
        init(jConsent, consentUUID);
    }

    private void init(JSONObject jConsent, String uuid) throws ConsentLibException {
        try {
            if(uuid == null) throw new IllegalArgumentException("uuid should not be null");
            this.uuid = uuid;
            acceptedVendors = json2StrArr(jConsent.getJSONArray("acceptedVendors"));
            acceptedCategories = json2StrArr(jConsent.getJSONArray("acceptedCategories"));
            specialFeatures = json2StrArr(jConsent.getJSONArray("specialFeatures"));
            legIntCategories = json2StrArr(jConsent.getJSONArray("legIntCategories"));
            consentString = jConsent.getString("euconsent");
            TCData = getHashMap(jConsent.getJSONObject("TCData"), logger);
            vendorGrants = new VendorGrants(jConsent.getJSONObject("grants"), logger);
        }
        catch (Exception e){
            //This general catch block is meant to deal with null pointer exceptions as well
            if(!(e instanceof ConsentLibException)){
                logger.error(new GenericSDKException(e, "Error parsing JSONObject to ConsentUser obj"));
            }
            throw new ConsentLibException(e, "Error parsing JSONObject to ConsentUser obj");
        }
    }

    protected ArrayList<String> json2StrArr(JSONArray jArray) throws JSONException {
        ArrayList<String> listData = new ArrayList();
        if (jArray != null) {
            for (int i=0;i<jArray.length();i++){
                listData.add(jArray.getString(i));
            }
        }
        return listData;
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        String tcdataString;
        try {
            tcdataString = getJson(TCData, logger).toString(2);
        } catch (Exception ignored) {
            tcdataString = TCData.toString();
        }
        return buffer.append("GDPRUserConsent(\n")
                .append("acceptedVendors: ").append(acceptedVendors).append("\n")
                .append("acceptedCategories: ").append(acceptedCategories).append("\n")
                .append("specialFeatures: ").append(specialFeatures).append("\n")
                .append("legIntCategories: ").append(legIntCategories).append("\n")
                .append("uuid: ").append(uuid).append("\n")
                .append("euconsent: ").append(consentString).append("\n")
                .append("TCData: ").append(tcdataString).append("\n")
                .append("vendorGrants: ").append(vendorGrants).append("\n")
                .append(")\n")
                .toString();
    }

    public JSONObject toJsonObject() throws JSONException, ConsentLibException {
        JSONObject jsonConsents = new JSONObject();
        jsonConsents.put("acceptedVendors", new JSONArray(acceptedVendors));
        jsonConsents.put("acceptedCategories", new JSONArray(acceptedCategories));
        jsonConsents.put("specialFeatures", new JSONArray(specialFeatures));
        jsonConsents.put("legIntCategories", new JSONArray(legIntCategories));
        jsonConsents.put("uuid", uuid);
        jsonConsents.put("euconsent", consentString);
        jsonConsents.put("TCData", getJson(TCData, logger));
        jsonConsents.put("grants", vendorGrants.toJsonObject());
        return jsonConsents;
    }

    public static class VendorGrants extends HashMap<String, VendorGrants.VendorGrant> {
        VendorGrants(JSONObject jVendorGrants, Logger logger) throws ConsentLibException {
            super();
            JSONArray names = jVendorGrants.names();
            if (names != null) {
                for(int i = 0; i < names.length(); i++) {
                    String name = getString(i, names, logger);
                    this.put(name, new VendorGrant(getJson(name, jVendorGrants, logger), logger));
                }
            }
        }
        VendorGrants(){ super(); }

        public JSONObject toJsonObject() throws JSONException, ConsentLibException {
            JSONObject json = new JSONObject();
            for(String key : keySet()){
                json.put(key, this.get(key).toJsonObject());
            }
            return json;
        }

        public String toString() {
            try {
                return this.toJsonObject().toString(2);
            } catch (Exception ignored) {
                return super.toString();
            }
        }

        public static class VendorGrant {
            public boolean vendorGrant;
            public HashMap<String, Boolean> purposeGrants;
            private Logger logger;

            VendorGrant(JSONObject jVendorGrant, Logger logger) throws ConsentLibException {
                vendorGrant = getBoolean("vendorGrant", jVendorGrant, logger);
                purposeGrants = getHashMap(getJson("purposeGrants", jVendorGrant, logger), logger);
                this.logger = logger;
            }

            public String toString(){
                return "{" + "vendorGrant=" + vendorGrant + ", " + "purposeGrants=" + purposeGrants + "}";
            }

            public JSONObject toJsonObject() throws ConsentLibException, JSONException {
                JSONObject json = new JSONObject();
                json.put("vendorGrant", vendorGrant);
                json.put("purposeGrants" , getJson(purposeGrants, logger));
                return json;
            }
        }
    }

}
