package sample.TestPojos;

import sample.OtherPojos.CSVRow;

import java.util.ArrayList;

public class CentralSelection {

    String TensileUnit;
    String TensileThickness;
    String TensileSTD;
    String TensileDirection;
    String TensileCustom;

    String ImpactSTD;
    String ImpactSize;
    String ImpactTestLoc;
    String ImpactTemp;
    String ImpactCustom;

    String BendDiameter;
    String BendDistance;
    String BendAngle;

    String HardnessMethod;
    String HardnessLoad;
    String HardnessTestType;

    String MicroMethod;
    String MicroLoad;
    String MicroTestType;
    String MicroStd;

   ArrayList<String> Materials = new ArrayList<>();
   ArrayList<String> Standards = new ArrayList<>();

    public ArrayList<String> getMaterials() {
        return Materials;
    }

    public void setMaterials(ArrayList<String> materials) {
        Materials = materials;
    }

    public ArrayList<String> getStandards() {
        return Standards;
    }

    public void setStandards(ArrayList<String> standards) {
        Standards = standards;
    }

    ArrayList<ArrayList<ParamObject>> AllStandardsList = new ArrayList<>();

    public ArrayList<ArrayList<ParamObject>> getAllStandardsList() {
        return AllStandardsList;
    }

    public void setAllStandardsList(ArrayList<ArrayList<ParamObject>> allStandardsList) {
        AllStandardsList = allStandardsList;
    }

    public String getMicroStd() {
        return MicroStd;
    }

    public void setMicroStd(String microStd) {
        MicroStd = microStd;
    }

    String MetaLocaion;

    public String getTensileUnit() {
        return TensileUnit;
    }

    public void setTensileUnit(String tensileUnit) {
        TensileUnit = tensileUnit;
    }

    public String getTensileThickness() {
        return TensileThickness;
    }

    public void setTensileThickness(String tensileThickness) {
        TensileThickness = tensileThickness;
    }

    public String getTensileSTD() {
        return TensileSTD;
    }

    public void setTensileSTD(String tensileSTD) {
        TensileSTD = tensileSTD;
    }

    public String getTensileDirection() {
        return TensileDirection;
    }

    public void setTensileDirection(String tensileDirection) {
        TensileDirection = tensileDirection;
    }

    public String getTensileCustom() {
        return TensileCustom;
    }

    public void setTensileCustom(String tensileCustom) {
        TensileCustom = tensileCustom;
    }

    public String getImpactSTD() {
        return ImpactSTD;
    }

    public void setImpactSTD(String impactSTD) {
        ImpactSTD = impactSTD;
    }

    public String getImpactSize() {
        return ImpactSize;
    }

    public void setImpactSize(String impactSize) {
        ImpactSize = impactSize;
    }

    public String getImpactTestLoc() {
        return ImpactTestLoc;
    }

    public void setImpactTestLoc(String impactTestLoc) {
        ImpactTestLoc = impactTestLoc;
    }

    public String getImpactTemp() {
        return ImpactTemp;
    }

    public void setImpactTemp(String impactTemp) {
        ImpactTemp = impactTemp;
    }

    public String getImpactCustom() {
        return ImpactCustom;
    }

    public void setImpactCustom(String impactCustom) {
        ImpactCustom = impactCustom;
    }

    public String getBendDiameter() {
        return BendDiameter;
    }

    public void setBendDiameter(String bendDiameter) {
        BendDiameter = bendDiameter;
    }

    public String getBendDistance() {
        return BendDistance;
    }

    public void setBendDistance(String bendDistance) {
        BendDistance = bendDistance;
    }

    public String getBendAngle() {
        return BendAngle;
    }

    public void setBendAngle(String bendAngle) {
        BendAngle = bendAngle;
    }

    public String getHardnessMethod() {
        return HardnessMethod;
    }

    public void setHardnessMethod(String hardnessMethod) {
        HardnessMethod = hardnessMethod;
    }

    public String getHardnessLoad() {
        return HardnessLoad;
    }

    public void setHardnessLoad(String hardnessLoad) {
        HardnessLoad = hardnessLoad;
    }

    public String getHardnessTestType() {
        return HardnessTestType;
    }

    public void setHardnessTestType(String hardnessTestType) {
        HardnessTestType = hardnessTestType;
    }

    public String getMicroMethod() {
        return MicroMethod;
    }

    public void setMicroMethod(String microMethod) {
        MicroMethod = microMethod;
    }

    public String getMicroLoad() {
        return MicroLoad;
    }

    public void setMicroLoad(String microLoad) {
        MicroLoad = microLoad;
    }

    public String getMicroTestType() {
        return MicroTestType;
    }

    public void setMicroTestType(String microTestType) {
        MicroTestType = microTestType;
    }

    public String getMetaLocaion() {
        return MetaLocaion;
    }

    public void setMetaLocaion(String metaLocaion) {
        MetaLocaion = metaLocaion;
    }

    String PreparerName;
    String ApproverName;
    String ApproverTitle;
    String PreparerTitle;

    public ArrayList<String> getHeaderClms() {
        return HeaderClms;
    }

    public void setHeaderClms(ArrayList<String> headerClms) {
        HeaderClms = headerClms;
    }

    ArrayList<String> HeaderClms = new ArrayList<>();

   public CSVRow ReportHeader = new CSVRow();

    public CSVRow getReportHeader() {
        return ReportHeader;
    }

    public void setReportHeader(CSVRow reportHeader) {
        ReportHeader = reportHeader;
    }

    public String ReportName;
    String StandardName;
    String SizeName;
    String ShapeName;
    String TestTypeName;
    String SpecificName;
    String GeneralName;
    String TestParamName;

    String CompanyName;

    String KSIorMPA;

    String Adress;

    String ContactName;

    String Phone;

    String Email;

    String Unit;

    String TensileSpecimen;

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String unit) {
        Unit = unit;
    }

    public String getTensileSpecimen() {
        return TensileSpecimen;
    }

    public void setTensileSpecimen(String tensileSpecimen) {
        TensileSpecimen = tensileSpecimen;
    }

    public String getContactName() {
        return ContactName;
    }

    public void setContactName(String contactName) {
        ContactName = contactName;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getAdress() {
        return Adress;
    }

    public void setAdress(String adress) {
        Adress = adress;
    }

    ArrayList<ParamObject> StandardData = new ArrayList<>();


    public ArrayList<ParamObject> getStandardData() {
        return StandardData;
    }

    public void setStandardData(ArrayList<ParamObject> standardData) {
        StandardData = standardData;
    }

    public String getKSIorMPA() {
        return KSIorMPA;
    }

    public void setKSIorMPA(String KSIorMPA) {
        this.KSIorMPA = KSIorMPA;
    }

    public String getStandardName() {
        return StandardName;
    }

    public void setStandardName(String standardName) {
        StandardName = standardName;
    }

    public String getSizeName() {
        return SizeName;
    }

    public void setSizeName(String sizeName) {
        SizeName = sizeName;
    }

    public String getShapeName() {
        return ShapeName;
    }

    public void setShapeName(String shapeName) {
        ShapeName = shapeName;
    }

    public String getTestTypeName() {
        return TestTypeName;
    }

    public void setTestTypeName(String testTypeName) {
        TestTypeName = testTypeName;
    }

    public String getSpecificName() {
        return SpecificName;
    }

    public void setSpecificName(String specificName) {
        SpecificName = specificName;
    }

    public String getGeneralName() {
        return GeneralName;
    }

    public void setGeneralName(String generalName) {
        GeneralName = generalName;
    }

    public String getTestParamName() {
        return TestParamName;
    }

    public void setTestParamName(String testParamName) {
        TestParamName = testParamName;
    }


    public String getReportName() {
        return ReportName;
    }

    public void setReportName(String reportName) {
        ReportName = reportName;
    }

    public String getApproverTitle() {
        return ApproverTitle;
    }

    public void setApproverTitle(String approverTitle) {
        ApproverTitle = approverTitle;
    }

    public String getPreparerTitle() {
        return PreparerTitle;
    }

    public void setPreparerTitle(String preparerTitle) {
        PreparerTitle = preparerTitle;
    }

    public String getPreparerName() {
        return PreparerName;
    }

    public void setPreparerName(String preparerName) {
        PreparerName = preparerName;
    }

    public String getApproverName() {
        return ApproverName;
    }

    public void setApproverName(String approverName) {
        ApproverName = approverName;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }
}
