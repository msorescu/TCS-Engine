//
// Translated by CS2J (http://www.cs2j.com): 10/5/2015 1:33:51 PM
//

package Mls.Request;

import CS2JNet.System.StringSupport;
import java.util.HashMap;
import java.util.Hashtable;

import Mls.*;
import engine.MLSCmaFields;
import engine.MLSRecord;
import engine.MLSRecords;
import engine.MLSUtil;
import Mls.TCServer;

public class GetAgentRoster  extends GetDataAggListings 
{
    protected static final String[] REQUIRED_FIELD = new String[]{  };
    public GetAgentRoster(TCServer tcs) throws Exception {
        super(tcs);
        TCSCount = new HashMap<String,Integer>();
        TCSCount.put("Agent", 0);
        TCSMLSNumbers.Add("Agent", new HashSet<String>());
    }

    protected public void checkSearchFields() throws Exception {
        Hashtable ht = m_connector.getSearchFields();
        String includeCDATA = "1";
        if (ht.containsKey("IncludeCDATA"))
            includeCDATA = (String)ht.get("IncludeCDATA");
         
        if (!StringSupport.isNullOrEmpty(includeCDATA))
        {
            m_includeCDATA = includeCDATA.equals("1");
        }
         
        String conditionCode = "";
        if (ht.containsKey("ST_ConditionCode"))
            conditionCode = (String)ht.get("ST_ConditionCode");
         
        if (!StringSupport.isNullOrEmpty(conditionCode) && !String.IsNullOrEmpty(m_connector.GetRETSQueryParameter()))
            throw TCSException.produceTCSException(TCSException.BOTH_RETSQUERYPARAMETER_CONDITIONCODE_USED);
         
    }

    protected public void processSearchFields() throws Exception {
        SetRecordLimit();
    }

    protected public void checkMLSRequiredSearchFeilds() throws Exception {
        return ;
    }

    protected public void getOutputXML() throws Exception {
        m_connector.WriteLine("GetOutPutXML");
        if (m_engine.IsDownloadIDs)
        {
            AddIDsForDownloadIDOnlySearch();
            return ;
        }
         
        boolean isDemo = m_engine.isDemoClient();
        boolean foundDemoListing = false;
        MLSCmaFields fields = null;
        fields = m_engine.getCmaFields();
        net.toppro.components.mls.engine.CmaField cf = fields.getField("STDFLastMod");
        boolean resultLastModMapped = true;
        String lastModifiedDateFormat = "";
        if (cf != null)
        {
            lastModifiedDateFormat = cf.getDateFormat();
            if (StringSupport.isNullOrEmpty(lastModifiedDateFormat))
            {
                if (cf.type == net.toppro.components.mls.engine.CmaField.CMA_FLDTYPE_DATETIME)
                    lastModifiedDateFormat = STANDARD_DATETIMEFORMAT;
                else
                    lastModifiedDateFormat = STANDARD_DATEFORMAT; 
            }
             
        }
        else
            resultLastModMapped = false; 
        if (StringSupport.isNullOrEmpty(lastModifiedDateFormat))
            lastModifiedDateFormat = "";
         
        m_records = m_engine.getMLSRecords(MLSRecords.FILTER_ALL);
        //m_connector.WriteLine("MLS Rercords: Records=" + m_records.size());
        MLSRecord rec = m_records.getFirstRecord();
        String fieldValue = "";
        String fieldName = "";
        boolean bAddField = true;
        while (rec != null)
        {
            m_resultBuffer.Append("<Agent>");
            String[] fl = new String[]{ "" };
            String dataStandardNoteId = "";
            String demoListDate = "";
            String saleDate = "";
            String listDate = "";
            String statusDate = "";
            String listPrice = "";
            String salePrice = "";
            String searchPrice = "";
            String bathRooms = "";
            String sqft = "";
            String stStatus = "";
            String recordID = "";
            String AgentID = "";
            String OfficeID = "";
            boolean goNextRec = false;
            for (int i = 0;i < getResultField().length;i++)
            {
                if (goNextRec)
                    break;
                 
                fieldValue = "";
                fieldName = TCSStandardResultFields.getXmlName(getResultField()[i]);
                //]field.getName();
                bAddField = true;
                String mlsValue = "";
                switch(getResultField()[i])
                {
                    case TCSStandardResultFields.STDF_AG_AGENTID:
                        fieldValue = rec.getTCSStdFieldValue(getResultField()[i],false);
                        recordID = fieldValue;
                        AgentID = fieldValue;
                        break;
                    case TCSStandardResultFields.STDF_AG_OFFICEID:
                        fieldValue = rec.getTCSStdFieldValue(getResultField()[i],false);
                        OfficeID = fieldValue;
                        break;
                    case TCSStandardResultFields.STDF_AG_AGENTIDTIGERLEAD:
                        fieldValue = rec.getTCSStdFieldValue(getResultField()[i],true);
                        if (StringSupport.isNullOrEmpty(fieldValue) && (fields.getField("AG_AgentIDTigerlead") == null))
                            fieldValue = AgentID;
                         
                        break;
                    case TCSStandardResultFields.STDF_AG_OFFICEIDTIGERLEAD:
                        fieldValue = rec.getTCSStdFieldValue(getResultField()[i],true);
                        if (StringSupport.isNullOrEmpty(fieldValue) && (fields.getField("AG_OfficeIDTigerlead") == null))
                            fieldValue = OfficeID;
                         
                        break;
                    case TCSStandardResultFields.STDF_AG_AGENTIDSHOWINGTIME:
                        fieldValue = rec.getTCSStdFieldValue(getResultField()[i],true);
                        if (StringSupport.isNullOrEmpty(fieldValue) && (fields.getField("AG_AgentIDShowingTime") == null))
                            fieldValue = AgentID;
                         
                        break;
                    case TCSStandardResultFields.STDF_AG_OFFICEIDSHOWINGTIME:
                        fieldValue = rec.getTCSStdFieldValue(getResultField()[i],true);
                        if (StringSupport.isNullOrEmpty(fieldValue) && (fields.getField("AG_OfficeIDShowingTime") == null))
                            fieldValue = OfficeID;
                         
                        break;
                    case TCSStandardResultFields.STDF_AG_LASTMOD:
                        fieldValue = rec.getTCSStdFieldValue(getResultField()[i],false);
                        if (fieldValue.indexOf('T') < 0)
                            fieldValue = MLSUtil.FormatDate(STANDARD_DATEFORMAT, STANDARD_DATETIMEFORMAT, fieldValue);
                         
                        if (fieldValue.indexOf("00:00:00") > -1)
                            fieldValue = fieldValue.replace("00:00:00", "23:59:59");
                         
                        break;
                    default: 
                        fieldValue = rec.getTCSStdFieldValue(getResultField()[i],false);
                        break;
                
                }
                if (bAddField && !(StringSupport.isNullOrEmpty(fieldValue)))
                    m_resultBuffer.Append("<" + fieldName + ">" + Util.convertStringToXML(Util.filterJunkValue(fieldValue)) + "</" + fieldName + ">");
                 
            }
            if (goNextRec)
            {
                int pos = m_resultBuffer.toString().lastIndexOf("<Agent");
                m_resultBuffer.Remove(pos, m_resultBuffer.Length - pos);
                rec = m_records.getNextRecord();
                continue;
            }
             
            String footNote = "";
            if (m_isAddWarningNote)
            {
                if (dataStandardNoteId.length() > 0)
                    footNote = m_warningNoteId + "," + dataStandardNoteId;
                else
                    footNote = m_warningNoteId + ""; 
            }
            else if (dataStandardNoteId.length() > 0)
                footNote = dataStandardNoteId;
              
            if (!StringSupport.isNullOrEmpty(footNote))
                m_resultBuffer.Append("<NoteID>").Append(footNote + "</NoteID>");
             
            m_resultBuffer.Append("</Agent>\r\n");
            TCSCount.put("Agent", TCSCount.get("Agent") + 1);
            TCSMLSNumbers.get("Agent").Add(recordID);
            rec = m_records.getNextRecord();
            m_overrideLimitCount++;
            if (IsOverrideLimit && m_overrideLimitCount > GetOverrideRecordLimit())
                break;
             
            CheckResultSize();
        }
    }

    protected public void addXMLHeader() throws Exception {
        if (m_connector.IsReturnDataAggListingsXml())
        {
            m_resultBuffer.Append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\r\n<TCResult ReplyCode=\"0\" ReplyText=\"Success\">\r\n<AgentRoster>\r\n");
        }
        else
            m_resultBuffer.Append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\r\n<TCResult>\r\n<AgentRoster>\r\n"); 
    }

    protected public void addXMLFooter() throws Exception {
        if (!m_connector.IsReturnDataAggListingsXml())
        {
            m_resultBuffer.Append("</AgentRoster>\r\n");
            addNotes();
            m_resultBuffer.Append("</TCResult>\r\n");
        }
        else
        {
            m_resultBuffer.Append("</AgentRoster>\r\n");
            addNotes();
        } 
    }

    public int[] getResultField() throws Exception {
        return TCSStandardResultFields.getAgentRosterResultFields();
    }

    protected public String[] getRequiredField() throws Exception {
        return REQUIRED_FIELD;
    }

}


