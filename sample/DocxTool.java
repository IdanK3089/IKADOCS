package sample;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;


import org.docx4j.XmlUtils;
import org.docx4j.dml.wordprocessingDrawing.Inline;
import org.docx4j.jaxb.Context;
import org.docx4j.model.structure.PageSizePaper;
import org.docx4j.model.structure.SectionWrapper;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage;
import org.docx4j.openpackaging.parts.WordprocessingML.FooterPart;
import org.docx4j.openpackaging.parts.WordprocessingML.HeaderPart;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.openpackaging.parts.WordprocessingML.NumberingDefinitionsPart;
import org.docx4j.openpackaging.parts.relationships.Namespaces;
import org.docx4j.openpackaging.parts.relationships.RelationshipsPart;
import org.docx4j.relationships.Relationship;
import org.docx4j.wml.Br;
import org.docx4j.wml.CTBookmark;
import org.docx4j.wml.CTMarkupRange;
import org.docx4j.wml.ContentAccessor;
import org.docx4j.wml.Drawing;
import org.docx4j.wml.FldChar;
import org.docx4j.wml.FooterReference;
import org.docx4j.wml.Ftr;
import org.docx4j.wml.Hdr;
import org.docx4j.wml.HdrFtrRef;
import org.docx4j.wml.HeaderReference;
import org.docx4j.wml.Numbering;
import org.docx4j.wml.ObjectFactory;
import org.docx4j.wml.P;
import org.docx4j.wml.P.Hyperlink;
import org.docx4j.wml.PPr;
import org.docx4j.wml.PPrBase.NumPr;
import org.docx4j.wml.PPrBase.NumPr.Ilvl;
import org.docx4j.wml.PPrBase.NumPr.NumId;
import org.docx4j.wml.PPrBase.PStyle;
import org.docx4j.wml.R;
import org.docx4j.wml.STBrType;
import org.docx4j.wml.STFldCharType;
import org.docx4j.wml.SectPr;
import org.docx4j.wml.Text;



/**
 * DOCX toolbox
 */
public class DocxTool
{
    private WordprocessingMLPackage wordMLPackage;
    private ObjectFactory factory;


    /** Empty constructor, use newDocument to generate the package. */
    public DocxTool()
    {
        factory = Context.getWmlObjectFactory();
    }
    /** Load from docx file */
    public DocxTool(File file)
    {
        this();
        open(file);
    }
    /** Load from docx stream */
    public DocxTool(InputStream in)
    {
        this();
        open(in);
    }
    /** Load from docx byte array */
    public DocxTool(byte[] b)
    {
        this();
        open(new ByteArrayInputStream(b));
    }

    /**
     * Open the document
     * @param file Docx file
     */
    public void open(File file)
    {
        try
        {
            FileInputStream in = new FileInputStream(file);
            open(in);
            in.close();
        }
        catch (Exception e)
        {
        }
    }

    /**
     * Open the document
     * @param in Docx input stream
     */
    public void open(InputStream in)
    {
        try
        {
            wordMLPackage = WordprocessingMLPackage.load(in);
        }
        catch(Exception e)
        {
        }
    }

    /** Create a new default document */
    public void newDocument()
    {
        try
        {
            wordMLPackage = WordprocessingMLPackage.createPackage();
        }
        catch (Exception e)
        {
        }
    }

    /**
     * Create a new document
     * @param sz default is PageSizePaper.A4
     * @param landscape horizontal or not
     */
    public void newDocument(PageSizePaper sz, boolean landscape)
    {
        try
        {
            if (sz==null) sz = PageSizePaper.A4;
            wordMLPackage = WordprocessingMLPackage.createPackage(sz, landscape);
        }
        catch (Exception e)
        {
        }
    }

    /** Add the default numbering part */
    public void addNumbering()
    {
        addNumbering(null);
    }

    /**
     * Add a numbering part
     * @param xml Numbering definition ex: getXmlNumbering(1234);
     */
    public void addNumbering(String xml)
    {
        try
        {
            NumberingDefinitionsPart ndp = new NumberingDefinitionsPart();
            wordMLPackage.getMainDocumentPart().addTargetPart(ndp);
            if (xml==null)
                ndp.unmarshalDefaultNumbering();
            else
                ndp.setJaxbElement((Numbering)XmlUtils.unmarshalString(xml));
        }
        catch (Exception e)
        {
        }
    }

    /** Get the whole document */
    public WordprocessingMLPackage getPackage()
    {
        return wordMLPackage;
    }
    /** Get the main document part */
    public MainDocumentPart getMainPart()
    {
        return wordMLPackage.getMainDocumentPart();
    }
    /** Get all main contents */
    public List<Object> getContent()
    {
        return wordMLPackage.getMainDocumentPart().getContent();
    }

    /** Convert the main part into XML */
    public String mainPartToString(boolean suppDecl, boolean prettyPrint)
    {
        return XmlUtils.marshaltoString(getMainPart(), suppDecl, prettyPrint);
    }
    /** Replace the main part with XML */
    public void stringToMainPart(String xml) throws JAXBException
    {
        getMainPart().setJaxbElement((org.docx4j.wml.Document)XmlUtils.unmarshalString(xml));
    }

    /** Get the document header parts */
    public List<HeaderPart> getHeaders()
    {
        List<HeaderPart> p = new ArrayList<>();
        try
        {
            RelationshipsPart rp = wordMLPackage.getMainDocumentPart().getRelationshipsPart();
            for (Relationship r : rp.getRelationships().getRelationship())
            {
                if (Namespaces.HEADER.equals(r.getType()))
                    p.add((HeaderPart)rp.getPart(r));
            }
        }
        catch(Exception e) {}
        return p;
    }

    /** Add a header to the document */
    public HeaderPart createHeader()
    {
        try
        {
            Hdr hdr = factory.createHdr();
            HeaderPart headerPart = new HeaderPart();
            Relationship rel =  wordMLPackage.getMainDocumentPart().addTargetPart(headerPart);
            headerPart.setJaxbElement(hdr);

            List<SectionWrapper> sections = wordMLPackage.getDocumentModel().getSections();
            SectPr sectPr = sections.get(sections.size() - 1).getSectPr();
            if (sectPr==null ) {
                sectPr = factory.createSectPr();
                wordMLPackage.getMainDocumentPart().addObject(sectPr);
                sections.get(sections.size() - 1).setSectPr(sectPr);
            }
            HeaderReference headerReference = factory.createHeaderReference();
            headerReference.setId(rel.getId());
            headerReference.setType(HdrFtrRef.DEFAULT);
            sectPr.getEGHdrFtrReferences().add(headerReference);
            return headerPart;
        }
        catch (Exception e) {}
        return null;
    }

    /** Get the document footer parts */
    public List<FooterPart> getFooters()
    {
        List<FooterPart> p = new ArrayList<>();
        try
        {
            RelationshipsPart rp = wordMLPackage.getMainDocumentPart().getRelationshipsPart();
            for (Relationship r : rp.getRelationships().getRelationship())
            {
                if (Namespaces.FOOTER.equals(r.getType()))
                    p.add((FooterPart)rp.getPart(r));
            }
        }
        catch(Exception e) {}
        return p;
    }

    /** Add a footer to the document */
    public FooterPart createFooter()
    {
        try
        {
            Ftr ftr = factory.createFtr();
            FooterPart footerPart = new FooterPart();
            Relationship rel =  wordMLPackage.getMainDocumentPart().addTargetPart(footerPart);
            footerPart.setJaxbElement(ftr);

            List<SectionWrapper> sections = wordMLPackage.getDocumentModel().getSections();
            SectPr sectPr = sections.get(sections.size() - 1).getSectPr();
            if (sectPr==null ) {
                sectPr = factory.createSectPr();
                wordMLPackage.getMainDocumentPart().addObject(sectPr);
                sections.get(sections.size() - 1).setSectPr(sectPr);
            }
            FooterReference footerReference = factory.createFooterReference();
            footerReference.setId(rel.getId());
            footerReference.setType(HdrFtrRef.DEFAULT);
            sectPr.getEGHdrFtrReferences().add(footerReference);
            return footerPart;
        }
        catch (Exception e) {}
        return null;
    }

    /**
     * Search objects corresponding to the xpath
     * @param xpath examples: //w:t to search all texts; //w:p paragraphs...
     */


    /**
     * Search objects corresponding to the class
     * @param obj starting point
     * @param cls examples: Text.class for texts, P.class to search paragraphs...
     */
    public List<Object> scan(Object obj, Class<?> cls)
    {
        return scan(obj, cls, new ArrayList<>());
    }
    private List<Object> scan(Object obj, Class<?> cls, List<Object> result)
    {
        if (obj==null) return new ArrayList<>();
        if (obj instanceof JAXBElement)
            obj = ((JAXBElement<?>)obj).getValue();

        if (obj.getClass().equals(cls))
            result.add(obj);

        if (obj instanceof ContentAccessor)
        {
            List<?> children = ((ContentAccessor)obj).getContent();
            for (Object child : children)
                scan(child, cls, result);
        }
        return result;
    }

    /** Get the first text in the docx object */
    public String getText(Object obj)
    {
        List<Object> list = scan(obj, Text.class);
        if (!list.isEmpty())
        {
            Text t = (Text)list.get(0);
            return t.getValue();
        }
        return "";
    }

    /** Get all texts in the docx object */
    public List<String> getTexts(Object obj)
    {
        List<String> res = new ArrayList<>();
        List<Object> list = scan(obj, Text.class);
        for (Object o : list)
        {
            Text t = (Text)o;
            res.add(t.getValue());
        }
        return res;
    }

    /** Get all texts in the document */
    public List<String> getTexts()
    {
        List<String> res = new ArrayList<>();
        List<Object> list = scan(getMainPart(), Text.class);
        for (Object o : list)
        {
            Text t = (Text)o;
            res.add(t.getValue());
        }
        return res;
    }

    public void replace(String toFind, String replacer)
    {
        replace(getMainPart(), toFind, replacer);
    }

    public void replace(Object part, String toFind, String replacer)
    {
        List<Object> paragraphs = scan(part, P.class);
        for (Object par : paragraphs)
        {
            P p = (P)par;
            List<Object> texts = scan(p, Text.class);
            for (Object text : texts)
            {
                Text t = (Text)text;
                if (t.getValue().contains(toFind))
                    t.setValue(t.getValue().replace(toFind, replacer));
            }
        }
    }

    /** Add something at the main part */
    public boolean add(Object o)
    {
        return o!=null ? getContent().add(o) : false;
    }
    /** Add objects at the main part */
    public boolean add(List<Object> list)
    {
        return list!=null ? getContent().addAll(list) : false;
    }
    /** Insert something in the main part */
    public void insert(int index, Object o)
    {
        if (o!=null) getContent().add(index, o);
    }
    /** Remove something in the main part */
    public Object remove(int index)
    {
        return index>=0 && index<getContent().size() ? getContent().remove(index) : null;
    }
    /** Remove something in the main part */
    public Object remove(Object o)
    {
        return o!=null ? getContent().remove(o) : null;
    }

    /** Build a simple text */
    public Text getText(String text)
    {
        /*
        <w:t>text</w:t>
         */
        Text t = factory.createText();
        t.setValue(text);
        t.setSpace("preserve");
        return t;
    }

    /** Build a simple run */
    public R getRun(String text)
    {
        /*
        <w:r>
            <w:t>text</w:t>
        </w:r>
        */
        R r = factory.createR();
        r.getContent().add(getText(text));
        return r;
    }

    /** Build a simple paragraph */
    public P getParagraph(String text)
    {
        /*
        <w:p>
            <w:r>
                <w:t>text</w:t>
            </w:r>
        </w:p>
        */
        P para = factory.createP();
        para.getContent().add(getRun(text));
        return para;
    }
    public void addParagraph(String text)
    {
        add(getParagraph(text));
    }
    public void insertParagraph(int index, String text)
    {
        insert(index, getParagraph(text));
    }

    /** Build a page break */
    public Br getPageBreak()
    {
        ObjectFactory factory = Context.getWmlObjectFactory();
        Br br = factory.createBr();
        br.setType(STBrType.PAGE);
        return br;
    }
    public void addPageBreak()
    {
        add(getPageBreak());
    }
    public void insertPageBreak(int index)
    {
        insert(index, getPageBreak());
    }

    /** Build a simple image in a paragraph */
    public P getImage(byte[] bytes, String filenameHint, String altText, int id1, int id2) throws Exception
    {
        BinaryPartAbstractImage imagePart = BinaryPartAbstractImage.createImagePart(wordMLPackage, bytes);
        Inline inline = imagePart.createImageInline(filenameHint, altText, id1, id2, false);

        // w:p / w:r / w:drawing
        P p = factory.createP();
        R run = factory.createR();
        p.getContent().add(run);
        Drawing drawing = factory.createDrawing();
        run.getContent().add(drawing);
        drawing.getAnchorOrInline().add(inline);

        return p;
    }

    public PPr getStyle(String style)
    {
        return getNum(style, -1, 0);
    }

    public PPr getNum(String style, long numId, long level)
    {
        /*
        <w:pPr>
            <w:pStyle w:val="Title" />
            <w:numPr>
                <w:ilvl w:val="level" />
                <w:numId w:val="0" />
            </w:numPr>
        </w:pPr>
        */
        if (style==null) style = STYLE_NORMAL;
        PStyle sty = factory.createPPrBasePStyle();
        sty.setVal(style);

        PPr ppr = factory.createPPr();
        ppr.setPStyle(sty);

        if (numId>=0) {
            NumId numid = factory.createPPrBaseNumPrNumId();
            numid.setVal(BigInteger.valueOf(numId));

            Ilvl lvl = factory.createPPrBaseNumPrIlvl();
            lvl.setVal(BigInteger.valueOf(level));

            NumPr numpr = factory.createPPrBaseNumPr();
            numpr.setNumId(numid);
            numpr.setIlvl(lvl);
            ppr.setNumPr(numpr);
        }
        return ppr;
    }

    public P getNumberedParagraph(String style, long numId, long level, String text)
    {
        return getNumberedParagraph(style, numId, level, text, -1, null);
    }

    public P getNumberedParagraph(String style, long numId, long level, String text, int bookmarkId, String bookmarkName)
    {
        /*
        <w:p>
            <w:pPr>
                <w:pStyle w:val="Title" />
                <w:numPr>
                    <w:ilvl w:val="level" />
                    <w:numId w:val="0" />
                </w:numPr>
            </w:pPr>
            <w:bookmarkStart w:id="0" w:name="_Ref411885859" />
            <w:r>
                <w:t>text</w:t>
            </w:r>
            <w:bookmarkEnd w:id="0" />
        </w:p>
        */

        if (style==null) style = STYLE_NORMAL;
        P para = factory.createP();
        para.getContent().add(getNum(style, numId, level));
        R r = getRun(text);
        para.getContent().add(r);

        if (bookmarkName!=null)
            createBookmark(para, r, bookmarkName, bookmarkId);

        return para;
    }

    /**
     * Add a numbered paragraph
     * @param style Default is Normal
     * @param numId Numbering id
     * @param level Level
     * @param text  Text
     */
    public void addNumberedParagraph(String style, long numId, long level, String text)
    {
        if (style==null) style = STYLE_NORMAL;
        add(getNumberedParagraph(style, numId, level, text));
    }

    /**
     * Add a numbered paragraph with bookmark
     * @param style Default is Normal
     * @param numId Numbering id
     * @param level Level
     * @param text  Text
     */
    public void addNumberedParagraph(String style, long numId, long level, String text, int bookmarkId, String bookmarkName)
    {
        if (style==null) style = STYLE_NORMAL;
        add(getNumberedParagraph(style, numId, level, text, bookmarkId, bookmarkName));
    }

    /**
     * Add a styled paragraph
     * @param style Default is Normal
     * @param text  Text
     */
    public void addStyledParagraph(String style, String text)
    {
        if (style==null) style = STYLE_NORMAL;
        getMainPart().addStyledParagraphOfText(style, text);
    }

    public P getStyledParagraph(String style, String text)
    {
        /*
        <w:p>
            <w:pPr>
                <w:pStyle w:val="Title" />
            </w:pPr>
            <w:r>
                <w:t>text</w:t>
            </w:r>
        </w:p>
        */
        if (style==null) style = STYLE_NORMAL;
        P para = factory.createP();
        para.getContent().add(getStyle(style));
        para.getContent().add(getRun(text));
        return para;
    }

    /**
     * Surround the specified run in the specified paragraph with a bookmark
     * @param p paragraph
     * @param r run
     * @param bookmark bookmark name
     * @param id bookmark id
     */
    public void createBookmark(P p, R r, String bookmark, int id)
    {
        int index = p.getContent().indexOf(r);
        if (index<0) return;
        BigInteger ID = BigInteger.valueOf(id);

        // Add bookmark end first
        CTMarkupRange mr = factory.createCTMarkupRange();
        mr.setId(ID);
        JAXBElement<CTMarkupRange> bmEnd = factory.createBodyBookmarkEnd(mr);
        p.getContent().add(index+1, bmEnd);

        // Next, bookmark start
        CTBookmark bm = factory.createCTBookmark();
        bm.setId(ID);
        bm.setName(bookmark);
        JAXBElement<CTBookmark> bmStart =  factory.createBodyBookmarkStart(bm);
        p.getContent().add(index, bmStart);
    }

    /**
     * Create a link to a bookmark (w:hyperlink)
     * @param bookmark bookmark name
     * @param label hyperlink label
     * @return Hyperlink link to add somewhere
     */
    public Hyperlink getHyperlink(String bookmark, String label)
    {
        /*<w:hyperlink w:anchor=\"" + bookmarkName + "\" xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\" " +
                "w:history=\"1\" >" +
                "<w:r>" +
                "<w:rPr>" +
                "<w:rStyle w:val=\"Hyperlink\" />" +  // TODO: enable this style in the document!
                "</w:rPr>" +
                "<w:t>" + linkText + "</w:t>" +
                "</w:r>" +
                "</w:hyperlink>"; */
        return MainDocumentPart.hyperlinkToBookmark(bookmark, label);
    }

    /** Surround the label with a bookmark text */
    public void replaceBookmark(P p, R r, String label, String bookmarkName)
    {
        /*
        <w:p>
        ...
            <w:r>
                <w:t xml:space="preserve">Click on the LABEL etc.</w:t>
            </w:r>
        ...
        </w:p>
    to
        <w:p>
        ...
            <w:r>
                <w:t xml:space="preserve">Click on the </w:t>
            </w:r>
            <w:r>
                <w:fldChar w:fldCharType="begin" />
            </w:r>
            <w:r>
                <w:instrText xml:space="preserve"> REF bookmarkName \r \h </w:instrText>
            </w:r>
            <w:r>
                <w:fldChar w:fldCharType="separate" />
            </w:r>
            <w:r>
                <w:t>LABEL</w:t>
            </w:r>
            <w:r>
                <w:fldChar w:fldCharType="end" />
            </w:r>
            <w:r>
                <w:t xml:space="preserve"> etc.</w:t>
            </w:r>
        ...
        </w:p>
        */
        try
        {
            int i = p.getContent().indexOf(r);
            if (i<0) return;



            FldChar b = factory.createFldChar();
            b.setFldCharType(STFldCharType.BEGIN);
            b.setDirty(true);
            R rb = factory.createR();
            rb.getContent().add(b);

            FldChar s = factory.createFldChar();
            s.setFldCharType(STFldCharType.SEPARATE);
            R rs = factory.createR();
            rs.getContent().add(s);

            FldChar e = factory.createFldChar();
            e.setFldCharType(STFldCharType.END);
            R re = factory.createR();
            re.getContent().add(e);

            Text txt = factory.createText();
            txt.setSpace("preserve");
            txt.setValue(" REF "+bookmarkName+" \\r \\h "); // Reference to the bookmark refresh + hyperlink
            JAXBElement<Text> ref = factory.createRInstrText(txt);
            R ri = factory.createR();
            ri.getContent().add(ref);

            p.getContent().remove(i);
            p.getContent().add(i+1, rb); // begin
            p.getContent().add(i+2, ri); // instr
            p.getContent().add(i+3, rs); // separate
            p.getContent().add(i+4, getRun(label)); // label
            p.getContent().add(i+5, re); // end
        }
        catch(Exception e)
        {
        }
    }



    /**
     * Convert any HTML to a well-formatted XHTML
     * @param html Source
     * @return XHTML or empty string in case of error
     */

    /**
     * Convert any HTML to a well-formatted XHTML
     * @param html Source
     * @param encoding Use the platform encoding when null
     * @return XHTML or empty string in case of error
     */


    /**
     * Convert any HTML to a well-formatted XHTML (jtidy implementation)
     * @param input HTML as input stream
     * @param encoding Use the platform encoding when null
     * @return XHTML or empty string in case of error
     */

    /**
     * Add table of contents
     * @param documentPart document part
     */
    public void addTableOfContent(MainDocumentPart documentPart, String depth)
    {
        P paragraph = factory.createP();

        addFieldBegin(paragraph, factory);
        addTableOfContentField(paragraph, factory, depth);
        addFieldEnd(paragraph, factory);

        documentPart.getContent().add(paragraph);
    }

    public P getTOCparagraph(String depth) {
        P paragraph = factory.createP();

        addFieldBegin(paragraph, factory);
        addTableOfContentField(paragraph, factory, depth);
        addFieldEnd(paragraph, factory);
        return paragraph;
    }

    public void addTableOfContentField(P paragraph, ObjectFactory objectFactory, String depth)
    {
        R run = objectFactory.createR();
        Text txt = new Text();
        txt.setSpace("preserve");
        txt.setValue(" TOC \\o \"1-"+depth+"\" \\h \\z \\u ");
        run.getContent().add(objectFactory.createRInstrText(txt));
        paragraph.getContent().add(run);
    }

    public void addFieldBegin(P paragraph, ObjectFactory objectFactory)
    {
        R run = objectFactory.createR();
        FldChar fldchar = objectFactory.createFldChar();
        fldchar.setFldCharType(STFldCharType.BEGIN);
        fldchar.setDirty(true);
        run.getContent().add(getWrappedFldChar(fldchar));
        paragraph.getContent().add(run);
    }

    public void addFieldEnd(P paragraph, ObjectFactory objectFactory)
    {
        R run = objectFactory.createR();
        FldChar fldcharend = objectFactory.createFldChar();
        fldcharend.setFldCharType(STFldCharType.END);
        run.getContent().add(getWrappedFldChar(fldcharend));
        paragraph.getContent().add(run);
    }

    public static JAXBElement<?> getWrappedFldChar(FldChar fldchar)
    {
        return new JAXBElement<>(new QName(Namespaces.NS_WORD12, "fldChar"), FldChar.class, fldchar);
    }

    public static final String STYLE_NORMAL   = "Normal";
    public static final String STYLE_TITLE    = "Title";
    public static final String STYLE_SUBTITLE = "SubTitle";

    /** Return w:numbering schema */
    public String getXmlNumbering(int numId)
    {
        return NUMBERING.replace("###NUMID###", Integer.toString(numId));
    }

    private static final String NUMBERING =
            "<w:numbering xmlns:ve=\"http://schemas.openxmlformats.org/markup-compatibility/2006\""
                    + " xmlns:o=\"urn:schemas-microsoft-com:office:office\""
                    + " xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\""
                    + " xmlns:m=\"http://schemas.openxmlformats.org/officeDocument/2006/math\""
                    + " xmlns:v=\"urn:schemas-microsoft-com:vml\""
                    + " xmlns:wp=\"http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing\""
                    + " xmlns:w10=\"urn:schemas-microsoft-com:office:word\""
                    + " xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\""
                    + " xmlns:wne=\"http://schemas.microsoft.com/office/word/2006/wordml\">"
                    + "<w:abstractNum w:abstractNumId=\"0\">"
                    + "<w:nsid w:val=\"2DD860C0\"/>"
                    + "<w:multiLevelType w:val=\"multilevel\"/>"
                    + "<w:tmpl w:val=\"0409001D\"/>"
                    + "<w:lvl w:ilvl=\"0\">" // Level 0
                    + "<w:start w:val=\"1\"/>"
                    + "<w:numFmt w:val=\"decimal\"/>"
                    + "<w:lvlText w:val=\"%1)\"/>"
                    + "<w:lvlJc w:val=\"left\"/>"
                    + "<w:pPr>"
                    + "<w:ind w:left=\"360\" w:hanging=\"360\"/>"
                    + "</w:pPr>"
                    + "</w:lvl>"
                    + "<w:lvl w:ilvl=\"1\">" // Level 1
                    + "<w:start w:val=\"1\"/>"
                    + "<w:numFmt w:val=\"lowerLetter\"/>"
                    + "<w:lvlText w:val=\"%2)\"/>"
                    + "<w:lvlJc w:val=\"left\"/>"
                    + "<w:pPr>"
                    + "<w:ind w:left=\"720\" w:hanging=\"360\"/>"
                    + "</w:pPr>"
                    + "</w:lvl>"
                    + "<w:lvl w:ilvl=\"2\">" // Level 2
                    + "<w:start w:val=\"1\"/>"
                    + "<w:numFmt w:val=\"lowerRoman\"/>"
                    + "<w:lvlText w:val=\"%3)\"/>"
                    + "<w:lvlJc w:val=\"left\"/>"
                    + "<w:pPr>"
                    + "<w:ind w:left=\"1080\" w:hanging=\"360\"/>"
                    + "</w:pPr>"
                    + "</w:lvl>"
                    + "<w:lvl w:ilvl=\"3\">" // Level 3
                    + "<w:start w:val=\"1\"/>"
                    + "<w:numFmt w:val=\"decimal\"/>"
                    + "<w:lvlText w:val=\"(%4)\"/>"
                    + "<w:lvlJc w:val=\"left\"/>"
                    + "<w:pPr>"
                    + "<w:ind w:left=\"1440\" w:hanging=\"360\"/>"
                    + "</w:pPr>"
                    + "</w:lvl>"
                    + "<w:lvl w:ilvl=\"4\">" // Level 4
                    + "<w:start w:val=\"1\"/>"
                    + "<w:numFmt w:val=\"lowerLetter\"/>"
                    + "<w:lvlText w:val=\"(%5)\"/>"
                    + "<w:lvlJc w:val=\"left\"/>"
                    + "<w:pPr>"
                    + "<w:ind w:left=\"1800\" w:hanging=\"360\"/>"
                    + "</w:pPr>"
                    + "</w:lvl>"
                    + "<w:lvl w:ilvl=\"5\">" // Level 5
                    + "<w:start w:val=\"1\"/>"
                    + "<w:numFmt w:val=\"lowerRoman\"/>"
                    + "<w:lvlText w:val=\"(%6)\"/>"
                    + "<w:lvlJc w:val=\"left\"/>"
                    + "<w:pPr>"
                    + "<w:ind w:left=\"2160\" w:hanging=\"360\"/>"
                    + "</w:pPr>"
                    + "</w:lvl>"
                    + "<w:lvl w:ilvl=\"6\">" // Level 6
                    + "<w:start w:val=\"1\"/>"
                    + "<w:numFmt w:val=\"decimal\"/>"
                    + "<w:lvlText w:val=\"%7.\"/>"
                    + "<w:lvlJc w:val=\"left\"/>"
                    + "<w:pPr>"
                    + "<w:ind w:left=\"2520\" w:hanging=\"360\"/>"
                    + "</w:pPr>"
                    + "</w:lvl>"
                    + "<w:lvl w:ilvl=\"7\">" // Level 7
                    + "<w:start w:val=\"1\"/>"
                    + "<w:numFmt w:val=\"lowerLetter\"/>"
                    + "<w:lvlText w:val=\"%8.\"/>"
                    + "<w:lvlJc w:val=\"left\"/>"
                    + "<w:pPr>"
                    + "<w:ind w:left=\"2880\" w:hanging=\"360\"/>"
                    + "</w:pPr>"
                    + "</w:lvl>"
                    + "<w:lvl w:ilvl=\"8\">" // Level 8
                    + "<w:start w:val=\"1\"/>"
                    + "<w:numFmt w:val=\"lowerRoman\"/>"
                    + "<w:lvlText w:val=\"%9.\"/>"
                    + "<w:lvlJc w:val=\"left\"/>"
                    + "<w:pPr>"
                    + "<w:ind w:left=\"3240\" w:hanging=\"360\"/>"
                    + "</w:pPr>"
                    + "</w:lvl>"
                    + "</w:abstractNum>"
                    + "<w:num w:numId=\"###NUMID###\">" // numId in doc
                    + "<w:abstractNumId w:val=\"0\"/>"
                    + "</w:num>"
                    + "</w:numbering>";

    /** Write the document into file */
    public void save(File file)
    {
        try
        {
            file.delete();
            FileOutputStream out = new FileOutputStream(file);
            save(out);
            out.close();
        }
        catch (Exception e)
        {
        }
    }

    /** Write the document into output stream */
    public void save(OutputStream out)
    {
        try
        {
            wordMLPackage.save(out);
        }
        catch (Exception e)
        {
        }
    }

    /** Get the document as a byte array (e.g. for usage as publication template or external objet output) */
    public byte[] toByteArray()
    {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        save(out);
        return out.toByteArray();
    }
}
