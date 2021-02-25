/*
 * Created on 17-ago-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.presentation.actions.question;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

import assesment.business.AssesmentAccess;
import assesment.communication.module.ModuleData;
import assesment.communication.question.AnswerData;
import assesment.communication.question.QuestionData;
import assesment.presentation.translator.web.util.Util;

public class QuestionForm extends ActionForm {

    private static final long serialVersionUID = 1L;

    private String target;
    
    private String question;
    private String module;
    private String key;
    private String es_text;
    private String en_text;
    private String pt_text;
    private String type;
    private FormFile imageName;
    private String testType;
    private String group;
    private String optioncount;

    private String es_video;
    private String en_video;
    private String pt_video;

    private FormFile es_videoFile;
    private FormFile en_videoFile;
    private FormFile pt_videoFile;

    private String es_videoKey;
    private String en_videoKey;
    private String pt_videoKey;

    private String answer1;
    private String key1;
    private String es_answer1;
    private String en_answer1;
    private String pt_answer1;
    private String answertype1;
    private String answer2;
    private String key2;
    private String es_answer2;
    private String en_answer2;
    private String pt_answer2;
    private String answertype2;
    private String answer3;
    private String key3;
    private String es_answer3;
    private String en_answer3;
    private String pt_answer3;
    private String answertype3;
    private String answer4;
    private String key4;
    private String es_answer4;
    private String en_answer4;
    private String pt_answer4;
    private String answertype4;
    private String answer5;
    private String key5;
    private String es_answer5;
    private String en_answer5;
    private String pt_answer5;
    private String answertype5;
    private String answer6;
    private String key6;
    private String es_answer6;
    private String en_answer6;
    private String pt_answer6;
    private String answertype6;
    private String answer7;
    private String key7;
    private String es_answer7;
    private String en_answer7;
    private String pt_answer7;
    private String answertype7;
    private String answer8;
    private String key8;
    private String es_answer8;
    private String en_answer8;
    private String pt_answer8;
    private String answertype8;
    private String answer9;
    private String key9;
    private String es_answer9;
    private String en_answer9;
    private String pt_answer9;
    private String answertype9;
    private String answer10;
    private String key10;
    private String es_answer10;
    private String en_answer10;
    private String pt_answer10;
    private String answertype10;
    private String answer11;
    private String key11;
    private String es_answer11;
    private String en_answer11;
    private String pt_answer11;
    private String answertype11;
    private String answer12;
    private String key12;
    private String es_answer12;
    private String en_answer12;
    private String pt_answer12;
    private String answertype12;
    private String answer13;
    private String key13;
    private String es_answer13;
    private String en_answer13;
    private String pt_answer13;
    private String answertype13;
    private String answer14;
    private String key14;
    private String es_answer14;
    private String en_answer14;
    private String pt_answer14;
    private String answertype14;
    private String answer15;
    private String key15;
    private String es_answer15;
    private String en_answer15;
    private String pt_answer15;
    private String answertype15;
    private String answer16;
    private String key16;
    private String es_answer16;
    private String en_answer16;
    private String pt_answer16;
    private String answertype16;
    private String answer17;
    private String key17;
    private String es_answer17;
    private String en_answer17;
    private String pt_answer17;
    private String answertype17;
    private String answer18;
    private String key18;
    private String es_answer18;
    private String en_answer18;
    private String pt_answer18;
    private String answertype18;
    private String answer19;
    private String key19;
    private String es_answer19;
    private String en_answer19;
    private String pt_answer19;
    private String answertype19;
    private String answer20;
    private String key20;
    private String es_answer20;
    private String en_answer20;
    private String pt_answer20;
    private String answertype20;
    private String answer21;
    private String key21;
    private String es_answer21;
    private String en_answer21;
    private String pt_answer21;
    private String answertype21;
    private String answer22;
    private String key22;
    private String es_answer22;
    private String en_answer22;
    private String pt_answer22;
    private String answertype22;
    private String answer23;
    private String key23;
    private String es_answer23;
    private String en_answer23;
    private String pt_answer23;
    private String answertype23;
    private String answer24;
    private String key24;
    private String es_answer24;
    private String en_answer24;
    private String pt_answer24;
    private String answertype24;
    private String answer25;
    private String key25;
    private String es_answer25;
    private String en_answer25;
    private String pt_answer25;
    private String answertype25;
    private String answer26;
    private String key26;
    private String es_answer26;
    private String en_answer26;
    private String pt_answer26;
    private String answertype26;
    private String answer27;
    private String key27;
    private String es_answer27;
    private String en_answer27;
    private String pt_answer27;
    private String answertype27;
    private String answer28;
    private String key28;
    private String es_answer28;
    private String en_answer28;
    private String pt_answer28;
    private String answertype28;
    private String answer29;
    private String key29;
    private String es_answer29;
    private String en_answer29;
    private String pt_answer29;
    private String answertype29;
    private String answer30;
    private String key30;
    private String es_answer30;
    private String en_answer30;
    private String pt_answer30;
    private String answertype30;


    private String order;

	private String wrt;

    public QuestionForm() {     
    }

    public QuestionForm(String module,String target) {
        this.module = module;
        type = "0";
        this.target = target;
    }

    public QuestionForm(QuestionData questionData,AssesmentAccess sys,String target) throws Exception {     
        question = String.valueOf(questionData.getId());
        String[] questionTexts = sys.getLanguageReportFacade().findTexts(questionData.getKey(),sys.getUserSessionData());
        key = questionData.getKey();
        es_text = questionTexts[0];
        en_text = questionTexts[1];
        pt_text = questionTexts[2];
        module = String.valueOf(questionData.getModule());
        type = String.valueOf(questionData.getType());
        group = (questionData.getGroup() == null) ? "0" : String.valueOf(questionData.getGroup());
        order = String.valueOf(questionData.getOrder());
        optioncount = String.valueOf(questionData.getAnswers().size());
        testType = String.valueOf(questionData.getTestType());
        wrt = String.valueOf(questionData.isWrt());
        this.target = target;
        
        if(questionData.getType().intValue() == QuestionData.VIDEO) {
            es_video = questionTexts[0];
            en_video = questionTexts[1];
            pt_video = questionTexts[2];
        }
        
        Iterator<AnswerData> it = questionData.getAnswerIterator();
        if(it.hasNext()) {
            AnswerData answer = it.next();
            answer1 = String.valueOf(answer.getId());
            key1 = answer.getKey();
            String[] answerTexts = sys.getLanguageReportFacade().findTexts(answer.getKey(),sys.getUserSessionData());
            es_answer1 = answerTexts[0];
            en_answer1 = answerTexts[1];
            pt_answer1 = answerTexts[2];
            answertype1 = String.valueOf(answer.getResultType());
        }
        if(it.hasNext()) {
            AnswerData answer = it.next();
            answer2 = String.valueOf(answer.getId());
            key2 = answer.getKey();
            String[] answerTexts = sys.getLanguageReportFacade().findTexts(answer.getKey(),sys.getUserSessionData());
            es_answer2 = answerTexts[0];
            en_answer2 = answerTexts[1];
            pt_answer2 = answerTexts[2];
            answertype2 = String.valueOf(answer.getResultType());
        }
        if(it.hasNext()) {
            AnswerData answer = it.next();
            answer3 = String.valueOf(answer.getId());
            key3 = answer.getKey();
            String[] answerTexts = sys.getLanguageReportFacade().findTexts(answer.getKey(),sys.getUserSessionData());
            es_answer3 = answerTexts[0];
            en_answer3 = answerTexts[1];
            pt_answer3 = answerTexts[2];
            answertype3 = String.valueOf(answer.getResultType());
        }
        if(it.hasNext()) {
            AnswerData answer = it.next();
            answer4 = String.valueOf(answer.getId());
            key4 = answer.getKey();
            String[] answerTexts = sys.getLanguageReportFacade().findTexts(answer.getKey(),sys.getUserSessionData());
            es_answer4 = answerTexts[0];
            en_answer4 = answerTexts[1];
            pt_answer4 = answerTexts[2];
            answertype4 = String.valueOf(answer.getResultType());
        }
        if(it.hasNext()) {
            AnswerData answer = it.next();
            answer5 = String.valueOf(answer.getId());
            key5 = answer.getKey();
            String[] answerTexts = sys.getLanguageReportFacade().findTexts(answer.getKey(),sys.getUserSessionData());
            es_answer5 = answerTexts[0];
            en_answer5 = answerTexts[1];
            pt_answer5 = answerTexts[2];
            answertype5 = String.valueOf(answer.getResultType());
        }
        if(it.hasNext()) {
            AnswerData answer = it.next();
            answer6 = String.valueOf(answer.getId());
            key6 = answer.getKey();
            String[] answerTexts = sys.getLanguageReportFacade().findTexts(answer.getKey(),sys.getUserSessionData());
            es_answer6 = answerTexts[0];
            en_answer6 = answerTexts[1];
            pt_answer6 = answerTexts[2];
            answertype6 = String.valueOf(answer.getResultType());
        }
        if(it.hasNext()) {
            AnswerData answer = it.next();
            answer7 = String.valueOf(answer.getId());
            key7 = answer.getKey();
            String[] answerTexts = sys.getLanguageReportFacade().findTexts(answer.getKey(),sys.getUserSessionData());
            es_answer7 = answerTexts[0];
            en_answer7 = answerTexts[1];
            pt_answer7 = answerTexts[2];
            answertype7 = String.valueOf(answer.getResultType());
        }
        if(it.hasNext()) {
            AnswerData answer = it.next();
            answer8 = String.valueOf(answer.getId());
            key8 = answer.getKey();
            String[] answerTexts = sys.getLanguageReportFacade().findTexts(answer.getKey(),sys.getUserSessionData());
            es_answer8 = answerTexts[0];
            en_answer8 = answerTexts[1];
            pt_answer8 = answerTexts[2];
            answertype8 = String.valueOf(answer.getResultType());
        }
        if(it.hasNext()) {
            AnswerData answer = it.next();
            answer9 = String.valueOf(answer.getId());
            key9 = answer.getKey();
            String[] answerTexts = sys.getLanguageReportFacade().findTexts(answer.getKey(),sys.getUserSessionData());
            es_answer9 = answerTexts[0];
            en_answer9 = answerTexts[1];
            pt_answer9 = answerTexts[2];
            answertype9 = String.valueOf(answer.getResultType());
        }
        if(it.hasNext()) {
            AnswerData answer = it.next();
            answer10 = String.valueOf(answer.getId());
            key10 = answer.getKey();
            String[] answerTexts = sys.getLanguageReportFacade().findTexts(answer.getKey(),sys.getUserSessionData());
            es_answer10 = answerTexts[0];
            en_answer10 = answerTexts[1];
            pt_answer10 = answerTexts[2];
            answertype10 = String.valueOf(answer.getResultType());
        }
        if(it.hasNext()) {
            AnswerData answer = it.next();
            answer11 = String.valueOf(answer.getId());
            key11 = answer.getKey();
            String[] answerTexts = sys.getLanguageReportFacade().findTexts(answer.getKey(),sys.getUserSessionData());
            es_answer11 = answerTexts[0];
            en_answer11 = answerTexts[1];
            pt_answer11 = answerTexts[2];
            answertype11 = String.valueOf(answer.getResultType());
        }
        if(it.hasNext()) {
            AnswerData answer = it.next();
            answer12 = String.valueOf(answer.getId());
            key12 = answer.getKey();
            String[] answerTexts = sys.getLanguageReportFacade().findTexts(answer.getKey(),sys.getUserSessionData());
            es_answer12 = answerTexts[0];
            en_answer12 = answerTexts[1];
            pt_answer12 = answerTexts[2];
            answertype12 = String.valueOf(answer.getResultType());
        }
        if(it.hasNext()) {
            AnswerData answer = it.next();
            answer13 = String.valueOf(answer.getId());
            key13 = answer.getKey();
            String[] answerTexts = sys.getLanguageReportFacade().findTexts(answer.getKey(),sys.getUserSessionData());
            es_answer13 = answerTexts[0];
            en_answer13 = answerTexts[1];
            pt_answer13 = answerTexts[2];
            answertype13 = String.valueOf(answer.getResultType());
        }
        if(it.hasNext()) {
            AnswerData answer = it.next();
            answer14 = String.valueOf(answer.getId());
            key14 = answer.getKey();
            String[] answerTexts = sys.getLanguageReportFacade().findTexts(answer.getKey(),sys.getUserSessionData());
            es_answer14 = answerTexts[0];
            en_answer14 = answerTexts[1];
            pt_answer14 = answerTexts[2];
            answertype14 = String.valueOf(answer.getResultType());
        }
        if(it.hasNext()) {
            AnswerData answer = it.next();
            answer15 = String.valueOf(answer.getId());
            key15 = answer.getKey();
            String[] answerTexts = sys.getLanguageReportFacade().findTexts(answer.getKey(),sys.getUserSessionData());
            es_answer15 = answerTexts[0];
            en_answer15 = answerTexts[1];
            pt_answer15 = answerTexts[2];
            answertype15 = String.valueOf(answer.getResultType());
        }
        if(it.hasNext()) {
            AnswerData answer = it.next();
            answer16 = String.valueOf(answer.getId());
            key16 = answer.getKey();
            String[] answerTexts = sys.getLanguageReportFacade().findTexts(answer.getKey(),sys.getUserSessionData());
            es_answer16 = answerTexts[0];
            en_answer16 = answerTexts[1];
            pt_answer16 = answerTexts[2];
            answertype16 = String.valueOf(answer.getResultType());
        }
        if(it.hasNext()) {
            AnswerData answer = it.next();
            answer17 = String.valueOf(answer.getId());
            key17 = answer.getKey();
            String[] answerTexts = sys.getLanguageReportFacade().findTexts(answer.getKey(),sys.getUserSessionData());
            es_answer17 = answerTexts[0];
            en_answer17 = answerTexts[1];
            pt_answer17 = answerTexts[2];
            answertype17 = String.valueOf(answer.getResultType());
        }
        if(it.hasNext()) {
            AnswerData answer = it.next();
            answer18 = String.valueOf(answer.getId());
            key18 = answer.getKey();
            String[] answerTexts = sys.getLanguageReportFacade().findTexts(answer.getKey(),sys.getUserSessionData());
            es_answer18 = answerTexts[0];
            en_answer18 = answerTexts[1];
            pt_answer18 = answerTexts[2];
            answertype18 = String.valueOf(answer.getResultType());
        }
        if(it.hasNext()) {
            AnswerData answer = it.next();
            answer19 = String.valueOf(answer.getId());
            key19 = answer.getKey();
            String[] answerTexts = sys.getLanguageReportFacade().findTexts(answer.getKey(),sys.getUserSessionData());
            es_answer19 = answerTexts[0];
            en_answer19 = answerTexts[1];
            pt_answer19 = answerTexts[2];
            answertype19 = String.valueOf(answer.getResultType());
        }
        if(it.hasNext()) {
            AnswerData answer = it.next();
            answer20 = String.valueOf(answer.getId());
            key20 = answer.getKey();
            String[] answerTexts = sys.getLanguageReportFacade().findTexts(answer.getKey(),sys.getUserSessionData());
            es_answer20 = answerTexts[0];
            en_answer20 = answerTexts[1];
            pt_answer20 = answerTexts[2];
            answertype20 = String.valueOf(answer.getResultType());
        }
        if(it.hasNext()) {
            AnswerData answer = it.next();
            answer21 = String.valueOf(answer.getId());
            key21 = answer.getKey();
            String[] answerTexts = sys.getLanguageReportFacade().findTexts(answer.getKey(),sys.getUserSessionData());
            es_answer21 = answerTexts[0];
            en_answer21 = answerTexts[1];
            pt_answer21 = answerTexts[2];
            answertype21 = String.valueOf(answer.getResultType());
        }
        if(it.hasNext()) {
            AnswerData answer = it.next();
            answer22 = String.valueOf(answer.getId());
            key22 = answer.getKey();
            String[] answerTexts = sys.getLanguageReportFacade().findTexts(answer.getKey(),sys.getUserSessionData());
            es_answer22 = answerTexts[0];
            en_answer22 = answerTexts[1];
            pt_answer22 = answerTexts[2];
            answertype22 = String.valueOf(answer.getResultType());
        }
        if(it.hasNext()) {
            AnswerData answer = it.next();
            answer23 = String.valueOf(answer.getId());
            key23 = answer.getKey();
            String[] answerTexts = sys.getLanguageReportFacade().findTexts(answer.getKey(),sys.getUserSessionData());
            es_answer23 = answerTexts[0];
            en_answer23 = answerTexts[1];
            pt_answer23 = answerTexts[2];
            answertype23 = String.valueOf(answer.getResultType());
        }
        if(it.hasNext()) {
            AnswerData answer = it.next();
            answer24 = String.valueOf(answer.getId());
            key24 = answer.getKey();
            String[] answerTexts = sys.getLanguageReportFacade().findTexts(answer.getKey(),sys.getUserSessionData());
            es_answer24 = answerTexts[0];
            en_answer24 = answerTexts[1];
            pt_answer24 = answerTexts[2];
            answertype24 = String.valueOf(answer.getResultType());
        }
        if(it.hasNext()) {
            AnswerData answer = it.next();
            answer25 = String.valueOf(answer.getId());
            key25 = answer.getKey();
            String[] answerTexts = sys.getLanguageReportFacade().findTexts(answer.getKey(),sys.getUserSessionData());
            es_answer25 = answerTexts[0];
            en_answer25 = answerTexts[1];
            pt_answer25 = answerTexts[2];
            answertype25 = String.valueOf(answer.getResultType());
        }
        if(it.hasNext()) {
            AnswerData answer = it.next();
            answer26 = String.valueOf(answer.getId());
            key26 = answer.getKey();
            String[] answerTexts = sys.getLanguageReportFacade().findTexts(answer.getKey(),sys.getUserSessionData());
            es_answer26 = answerTexts[0];
            en_answer26 = answerTexts[1];
            pt_answer26 = answerTexts[2];
            answertype26 = String.valueOf(answer.getResultType());
        }
        if(it.hasNext()) {
            AnswerData answer = it.next();
            answer27 = String.valueOf(answer.getId());
            key27 = answer.getKey();
            String[] answerTexts = sys.getLanguageReportFacade().findTexts(answer.getKey(),sys.getUserSessionData());
            es_answer27 = answerTexts[0];
            en_answer27 = answerTexts[1];
            pt_answer27 = answerTexts[2];
            answertype27 = String.valueOf(answer.getResultType());
        }
        if(it.hasNext()) {
            AnswerData answer = it.next();
            answer28 = String.valueOf(answer.getId());
            key28 = answer.getKey();
            String[] answerTexts = sys.getLanguageReportFacade().findTexts(answer.getKey(),sys.getUserSessionData());
            es_answer28 = answerTexts[0];
            en_answer28 = answerTexts[1];
            pt_answer28 = answerTexts[2];
            answertype28 = String.valueOf(answer.getResultType());
        }
        if(it.hasNext()) {
            AnswerData answer = it.next();
            answer29 = String.valueOf(answer.getId());
            key29 = answer.getKey();
            String[] answerTexts = sys.getLanguageReportFacade().findTexts(answer.getKey(),sys.getUserSessionData());
            es_answer29 = answerTexts[0];
            en_answer29 = answerTexts[1];
            pt_answer29 = answerTexts[2];
            answertype29 = String.valueOf(answer.getResultType());
        }
        if(it.hasNext()) {
            AnswerData answer = it.next();
            answer30 = String.valueOf(answer.getId());
            key30 = answer.getKey();
            String[] answerTexts = sys.getLanguageReportFacade().findTexts(answer.getKey(),sys.getUserSessionData());
            es_answer30 = answerTexts[0];
            en_answer30 = answerTexts[1];
            pt_answer30 = answerTexts[2];
            answertype30 = String.valueOf(answer.getResultType());
        }
    }

    public String getEn_text() {
        return en_text;
    }

    public String getEs_text() {
        return es_text;
    }

    public String getModule() {
        return module;
    }

    public String getOptioncount() {
        return optioncount;
    }

    public String getPt_text() {
        return pt_text;
    }

    public String getQuestion() {
        return question;
    }

    public String getType() {
        return type;
    }

    public void setEn_text(String en_text) {
        this.en_text = en_text;
    }

    public void setEs_text(String es_text) {
        this.es_text = es_text;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public void setOptioncount(String optioncount) {
        this.optioncount = optioncount;
    }

    public void setPt_text(String pt_text) {
        this.pt_text = pt_text;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEn_answer1() {
        return en_answer1;
    }

    public String getEn_answer2() {
        return en_answer2;
    }

    public String getEn_answer3() {
        return en_answer3;
    }

    public String getEn_answer4() {
        return en_answer4;
    }

    public String getEn_answer5() {
        return en_answer5;
    }

    public String getEs_answer1() {
        return es_answer1;
    }

    public String getEs_answer2() {
        return es_answer2;
    }

    public String getEs_answer3() {
        return es_answer3;
    }

    public String getEs_answer4() {
        return es_answer4;
    }

    public String getEs_answer5() {
        return es_answer5;
    }

    public String getPt_answer1() {
        return pt_answer1;
    }

    public String getPt_answer2() {
        return pt_answer2;
    }

    public String getPt_answer3() {
        return pt_answer3;
    }

    public String getPt_answer4() {
        return pt_answer4;
    }

    public String getPt_answer5() {
        return pt_answer5;
    }

    public void setEn_answer1(String en_answer1) {
        this.en_answer1 = en_answer1;
    }

    public void setEn_answer2(String en_answer2) {
        this.en_answer2 = en_answer2;
    }

    public void setEn_answer3(String en_answer3) {
        this.en_answer3 = en_answer3;
    }

    public void setEn_answer4(String en_answer4) {
        this.en_answer4 = en_answer4;
    }

    public void setEn_answer5(String en_answer5) {
        this.en_answer5 = en_answer5;
    }

    public void setEs_answer1(String es_answer1) {
        this.es_answer1 = es_answer1;
    }

    public void setEs_answer2(String es_answer2) {
        this.es_answer2 = es_answer2;
    }

    public void setEs_answer3(String es_answer3) {
        this.es_answer3 = es_answer3;
    }

    public void setEs_answer4(String es_answer4) {
        this.es_answer4 = es_answer4;
    }

    public void setEs_answer5(String es_answer5) {
        this.es_answer5 = es_answer5;
    }

    public void setPt_answer1(String pt_answer1) {
        this.pt_answer1 = pt_answer1;
    }

    public void setPt_answer2(String pt_answer2) {
        this.pt_answer2 = pt_answer2;
    }

    public void setPt_answer3(String pt_answer3) {
        this.pt_answer3 = pt_answer3;
    }

    public void setPt_answer4(String pt_answer4) {
        this.pt_answer4 = pt_answer4;
    }

    public void setPt_answer5(String pt_answer5) {
        this.pt_answer5 = pt_answer5;
    }

    public String getAnswertype1() {
        return answertype1;
    }

    public String getAnswertype2() {
        return answertype2;
    }

    public String getAnswertype3() {
        return answertype3;
    }

    public String getAnswertype4() {
        return answertype4;
    }

    public String getAnswertype5() {
        return answertype5;
    }

    public void setAnswertype1(String answertype1) {
        this.answertype1 = answertype1;
    }

    public void setAnswertype2(String answertype2) {
        this.answertype2 = answertype2;
    }

    public void setAnswertype3(String answertype3) {
        this.answertype3 = answertype3;
    }

    public void setAnswertype4(String answertype4) {
        this.answertype4 = answertype4;
    }

    public void setAnswertype5(String answertype5) {
        this.answertype5 = answertype5;
    }
    
    public String validate() throws Exception {
        if(Integer.parseInt(type) == QuestionData.VIDEO) {
        	if(es_video.equals("new")) {
        		if(es_videoFile.getFileData().length == 0) {
        			return "question.video.empty_es";
        		}else if(!es_videoFile.getFileName().toLowerCase().endsWith(".mp4")) {
        			return "question.video.wrong_es";
        		}
        		if(empty(es_videoKey)) {
        			return "question.video.emptydescription_es";
        		}
        	}
        	if(en_video.equals("new")) {
        		if(en_videoFile == null) {
        			return "question.video.empty_en";
        		}else if(!en_videoFile.getFileName().toLowerCase().endsWith(".mp4")) {
        			return "question.video.wrong_en";
        		}
        		if(empty(en_videoKey)) {
        			return "question.video.emptydescription_en";
        		}
        	}
        	if(pt_video.equals("new")) {
        		if(pt_videoFile == null) {
        			return "question.video.empty_pt";
        		}else if(!pt_videoFile.getFileName().toLowerCase().endsWith(".mp4")) {
        			return "question.video.wrong_pt";
        		}
        		if(empty(pt_videoKey)) {
        			return "question.video.emptydescription_pt";
        		}
        	}
        }else {    	
	        if(empty(es_text) || empty(en_text) || empty(pt_text)) {
	            return "question.error.emptytext";
	        }
	        if(empty(type)) {
	            return "question.error.emptytype";
	        }
	        if(Integer.parseInt(type) == 3) {
	            for(int i = 1; i <= Integer.parseInt(optioncount); i++) {
	                if(empty(getAnswer(0,i)) || empty(getAnswer(1,i)) || empty(getAnswer(2,i))) {
	                    return "question.error.emptyanswertext";
	                }
	                if(empty(getAnswerType(i))) {
	                    return "question.error.emptyanswertype";
	                }
	            }
	        }
        }
        return null;
    }

    public String getAnswer1() {
        return answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public String getAnswer4() {
        return answer4;
    }

    public String getAnswer5() {
        return answer5;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public void setAnswer4(String answer4) {
        this.answer4 = answer4;
    }

    public void setAnswer5(String answer5) {
        this.answer5 = answer5;
    }

    public QuestionData getData(ModuleData moduleData) {
        QuestionData questionData = new QuestionData();
        if(!empty(question)) {
            questionData.setId(new Integer(question));
        }
        int questionType = new Integer(type).intValue();
        questionData.setModule(new Integer(module));
        questionData.setKey(key);
        questionData.setType(new Integer(type));
        if(group != null) {
            questionData.setGroup(new Integer(group));
        }
        if(empty(question)) {
            questionData.setOrder(new Integer(moduleData.getQuestions().size()+1));
        }else {
            questionData.setOrder(new Integer(order));
        }
        if(!type.equals(String.valueOf(QuestionData.YOU_TUBE_VIDEO))) { 
	        if(imageName != null && !Util.empty(imageName.getFileName())) {
	            questionData.setImage(imageName.getFileName());
	        }
    	}
        questionData.setTestType(new Integer(testType));
        
        if(type.equals(String.valueOf(QuestionData.EXCLUDED_OPTIONS)) 
                || type.equals(String.valueOf(QuestionData.LIST))
                || type.equals(String.valueOf(QuestionData.INCLUDED_OPTIONS))
                || type.equals(String.valueOf(QuestionData.KILOMETERS))) {
            Collection<AnswerData> answers = new LinkedList<AnswerData>();
            for(int i = 1; i <= Integer.parseInt(optioncount); i++) {
                AnswerData answer = new AnswerData();
                if(!empty(getAnswer(i))) {
                    answer.setId(new Integer(getAnswer(i)));
                }
                answer.setKey(getKey(i));
                answer.setOrder(new Integer(i));
                answer.setResultType(new Integer(getAnswerType(i)));
                answers.add(answer);
            }
            questionData.setAnswers(answers);
        }
        questionData.setWrt(Boolean.parseBoolean(wrt));
        return questionData;
    }
    
    public String[] getQuestionTexts() {
        String[] values = {es_text,en_text,pt_text};
        return values;
    }

    public String[][] getAnswerTexts() {
        String[][] values = new String[Integer.parseInt(optioncount)][3];
        for(int i = 1; i <= Integer.parseInt(optioncount); i++) {
            values[i-1][0] = getAnswer(0,i);
            values[i-1][1] = getAnswer(1,i);
            values[i-1][2] = getAnswer(2,i);
        }
        return values;
    }

    public String getAnswer(int index) {
        switch(index) {
            case 1:
                return answer1;
            case 2:
                return answer2;
            case 3:
                return answer3;
            case 4:
                return answer4;
            case 5:
                return answer5;
            case 6:
                return answer6;
            case 7:
                return answer7;
            case 8:
                return answer8;
            case 9:
                return answer9;
            case 10:
                return answer10;
            case 11:
                return answer11;
            case 12:
                return answer12;
            case 13:
                return answer13;
            case 14:
                return answer14;
            case 15:
                return answer15;
            case 16:
                return answer16;
            case 17:
                return answer17;
            case 18:
                return answer18;
            case 19:
                return answer19;
            case 20:
                return answer20;
            case 21:
                return answer21;
            case 22:
                return answer22;
            case 23:
                return answer23;
            case 24:
                return answer24;
            case 25:
                return answer25;
            case 26:
                return answer26;
            case 27:
                return answer27;
            case 28:
                return answer28;
            case 29:
                return answer29;
            case 30:
                return answer30;
        }
        return null;
    }
        
    public String getKey(int index) {
        switch(index) {
            case 1:
                return key1;
            case 2:
                return key2;
            case 3:
                return key3;
            case 4:
                return key4;
            case 5:
                return key5;
            case 6:
                return key6;
            case 7:
                return key7;
            case 8:
                return key8;
            case 9:
                return key9;
            case 10:
                return key10;
            case 11:
                return key11;
            case 12:
                return key12;
            case 13:
                return key13;
            case 14:
                return key14;
            case 15:
                return key15;
            case 16:
                return key16;
            case 17:
                return key17;
            case 18:
                return key18;
            case 19:
                return key19;
            case 20:
                return key20;
            case 21:
                return key21;
            case 22:
                return key22;
            case 23:
                return key23;
            case 24:
                return key24;
            case 25:
                return key25;
            case 26:
                return key26;
            case 27:
                return key27;
            case 28:
                return key28;
            case 29:
                return key29;
            case 30:
                return key30;
        }
        return null;
    }

    private String getAnswer(int language, int index) {
        switch(language) {
            case 0:
                switch(index) {
                    case 1:
                        return es_answer1;
                    case 2:
                        return es_answer2;
                    case 3:
                        return es_answer3;
                    case 4:
                        return es_answer4;
                    case 5:
                        return es_answer5;
                    case 6:
                        return es_answer6;
                    case 7:
                        return es_answer7;
                    case 8:
                        return es_answer8;
                    case 9:
                        return es_answer9;
                    case 10:
                        return es_answer10;
                    case 11:
                        return es_answer11;
                    case 12:
                        return es_answer12;
                    case 13:
                        return es_answer13;
                    case 14:
                        return es_answer14;
                    case 15:
                        return es_answer15;
                    case 16:
                        return es_answer16;
                    case 17:
                        return es_answer17;
                    case 18:
                        return es_answer18;
                    case 19:
                        return es_answer19;
                    case 20:
                        return es_answer20;
                    case 21:
                        return es_answer21;
                    case 22:
                        return es_answer22;
                    case 23:
                        return es_answer23;
                    case 24:
                        return es_answer24;
                    case 25:
                        return es_answer25;
                    case 26:
                        return es_answer26;
                    case 27:
                        return es_answer27;
                    case 28:
                        return es_answer28;
                    case 29:
                        return es_answer29;
                    case 30:
                        return es_answer30;
                }
            case 1:
                switch(index) {
                    case 1:
                        return en_answer1;
                    case 2:
                        return en_answer2;
                    case 3:
                        return en_answer3;
                    case 4:
                        return en_answer4;
                    case 5:
                        return en_answer5;
                    case 6:
                        return en_answer6;
                    case 7:
                        return en_answer7;
                    case 8:
                        return en_answer8;
                    case 9:
                        return en_answer9;
                    case 10:
                        return en_answer10;
                    case 11:
                        return en_answer11;
                    case 12:
                        return en_answer12;
                    case 13:
                        return en_answer13;
                    case 14:
                        return en_answer14;
                    case 15:
                        return en_answer15;
                    case 16:
                        return en_answer16;
                    case 17:
                        return en_answer17;
                    case 18:
                        return en_answer18;
                    case 19:
                        return en_answer19;
                    case 20:
                        return en_answer20;
                    case 21:
                        return en_answer21;
                    case 22:
                        return en_answer22;
                    case 23:
                        return en_answer23;
                    case 24:
                        return en_answer24;
                    case 25:
                        return en_answer25;
                    case 26:
                        return en_answer26;
                    case 27:
                        return en_answer27;
                    case 28:
                        return en_answer28;
                    case 29:
                        return en_answer29;
                    case 30:
                        return en_answer30;
                }
            case 2:
                switch(index) {
                    case 1:
                        return pt_answer1;
                    case 2:
                        return pt_answer2;
                    case 3:
                        return pt_answer3;
                    case 4:
                        return pt_answer4;
                    case 5:
                        return pt_answer5;
                    case 6:
                        return pt_answer6;
                    case 7:
                        return pt_answer7;
                    case 8:
                        return pt_answer8;
                    case 9:
                        return pt_answer9;
                    case 10:
                        return pt_answer10;
                    case 11:
                        return pt_answer11;
                    case 12:
                        return pt_answer12;
                    case 13:
                        return pt_answer13;
                    case 14:
                        return pt_answer14;
                    case 15:
                        return pt_answer15;
                    case 16:
                        return pt_answer16;
                    case 17:
                        return pt_answer17;
                    case 18:
                        return pt_answer18;
                    case 19:
                        return pt_answer19;
                    case 20:
                        return pt_answer20;
                    case 21:
                        return pt_answer21;
                    case 22:
                        return pt_answer22;
                    case 23:
                        return pt_answer23;
                    case 24:
                        return pt_answer24;
                    case 25:
                        return pt_answer25;
                    case 26:
                        return pt_answer26;
                    case 27:
                        return pt_answer27;
                    case 28:
                        return pt_answer28;
                    case 29:
                        return pt_answer29;
                    case 30:
                        return pt_answer30;
                }
        }
        return null;
    }

    private String getAnswerType(int index) {
        switch(index) {
            case 1:
                return answertype1;
            case 2:
                return answertype2;
            case 3:
                return answertype3;
            case 4:
                return answertype4;
            case 5:
                return answertype5;
            case 6:
                return answertype6;
            case 7:
                return answertype7;
            case 8:
                return answertype8;
            case 9:
                return answertype9;
            case 10:
                return answertype10;
            case 11:
                return answertype11;
            case 12:
                return answertype12;
            case 13:
                return answertype13;
            case 14:
                return answertype14;
            case 15:
                return answertype15;
            case 16:
                return answertype16;
            case 17:
                return answertype17;
            case 18:
                return answertype18;
            case 19:
                return answertype19;
            case 20:
                return answertype20;
            case 21:
                return answertype21;
            case 22:
                return answertype22;
            case 23:
                return answertype23;
            case 24:
                return answertype24;
            case 25:
                return answertype25;
            case 26:
                return answertype26;
            case 27:
                return answertype27;
            case 28:
                return answertype28;
            case 29:
                return answertype29;
            case 30:
                return answertype30;
        }
        return null;
    }

    private boolean empty(String text) {
        return (text == null || text.length() == 0);
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getKey() {
        return key;
    }

    public String getKey1() {
        return key1;
    }

    public String getKey2() {
        return key2;
    }

    public String getKey3() {
        return key3;
    }

    public String getKey4() {
        return key4;
    }

    public String getKey5() {
        return key5;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setKey1(String key1) {
        this.key1 = key1;
    }

    public void setKey2(String key2) {
        this.key2 = key2;
    }

    public void setKey3(String key3) {
        this.key3 = key3;
    }

    public void setKey4(String key4) {
        this.key4 = key4;
    }

    public void setKey5(String key5) {
        this.key5 = key5;
    }

    public FormFile getImageName() {
        return imageName;
    }

    public void setImageName(FormFile imageName) {
        this.imageName = imageName;
    }

    public String getTestType() {
        return testType;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getAnswertype10() {
        return answertype10;
    }

    public String getAnswertype11() {
        return answertype11;
    }

    public String getAnswertype12() {
        return answertype12;
    }

    public String getAnswertype13() {
        return answertype13;
    }

    public String getAnswertype14() {
        return answertype14;
    }

    public String getAnswertype15() {
        return answertype15;
    }

    public String getAnswertype16() {
        return answertype16;
    }

    public String getAnswertype17() {
        return answertype17;
    }

    public String getAnswertype18() {
        return answertype18;
    }

    public String getAnswertype19() {
        return answertype19;
    }

    public String getAnswertype20() {
        return answertype20;
    }

    public String getAnswertype6() {
        return answertype6;
    }

    public String getAnswertype7() {
        return answertype7;
    }

    public String getAnswertype8() {
        return answertype8;
    }

    public String getAnswertype9() {
        return answertype9;
    }

    public String getEn_answer10() {
        return en_answer10;
    }

    public String getEn_answer11() {
        return en_answer11;
    }

    public String getEn_answer12() {
        return en_answer12;
    }

    public String getEn_answer13() {
        return en_answer13;
    }

    public String getEn_answer14() {
        return en_answer14;
    }

    public String getEn_answer15() {
        return en_answer15;
    }

    public String getEn_answer16() {
        return en_answer16;
    }

    public String getEn_answer17() {
        return en_answer17;
    }

    public String getEn_answer18() {
        return en_answer18;
    }

    public String getEn_answer19() {
        return en_answer19;
    }

    public String getEn_answer20() {
        return en_answer20;
    }

    public String getEn_answer6() {
        return en_answer6;
    }

    public String getEn_answer7() {
        return en_answer7;
    }

    public String getEn_answer8() {
        return en_answer8;
    }

    public String getEn_answer9() {
        return en_answer9;
    }

    public String getEs_answer10() {
        return es_answer10;
    }

    public String getEs_answer11() {
        return es_answer11;
    }

    public String getEs_answer12() {
        return es_answer12;
    }

    public String getEs_answer13() {
        return es_answer13;
    }

    public String getEs_answer14() {
        return es_answer14;
    }

    public String getEs_answer15() {
        return es_answer15;
    }

    public String getEs_answer16() {
        return es_answer16;
    }

    public String getEs_answer17() {
        return es_answer17;
    }

    public String getEs_answer18() {
        return es_answer18;
    }

    public String getEs_answer19() {
        return es_answer19;
    }

    public String getEs_answer20() {
        return es_answer20;
    }

    public String getEs_answer6() {
        return es_answer6;
    }

    public String getEs_answer7() {
        return es_answer7;
    }

    public String getEs_answer8() {
        return es_answer8;
    }

    public String getEs_answer9() {
        return es_answer9;
    }

    public String getKey10() {
        return key10;
    }

    public String getKey11() {
        return key11;
    }

    public String getKey12() {
        return key12;
    }

    public String getKey13() {
        return key13;
    }

    public String getKey14() {
        return key14;
    }

    public String getKey15() {
        return key15;
    }

    public String getKey16() {
        return key16;
    }

    public String getKey17() {
        return key17;
    }

    public String getKey18() {
        return key18;
    }

    public String getKey19() {
        return key19;
    }

    public String getKey20() {
        return key20;
    }

    public String getKey6() {
        return key6;
    }

    public String getKey7() {
        return key7;
    }

    public String getKey8() {
        return key8;
    }

    public String getKey9() {
        return key9;
    }

    public String getPt_answer10() {
        return pt_answer10;
    }

    public String getPt_answer11() {
        return pt_answer11;
    }

    public String getPt_answer12() {
        return pt_answer12;
    }

    public String getPt_answer13() {
        return pt_answer13;
    }

    public String getPt_answer14() {
        return pt_answer14;
    }

    public String getPt_answer15() {
        return pt_answer15;
    }

    public String getPt_answer16() {
        return pt_answer16;
    }

    public String getPt_answer17() {
        return pt_answer17;
    }

    public String getPt_answer18() {
        return pt_answer18;
    }

    public String getPt_answer19() {
        return pt_answer19;
    }

    public String getPt_answer20() {
        return pt_answer20;
    }

    public String getPt_answer6() {
        return pt_answer6;
    }

    public String getPt_answer7() {
        return pt_answer7;
    }

    public String getPt_answer8() {
        return pt_answer8;
    }

    public String getPt_answer9() {
        return pt_answer9;
    }

    public void setAnswertype10(String answertype10) {
        this.answertype10 = answertype10;
    }

    public void setAnswertype11(String answertype11) {
        this.answertype11 = answertype11;
    }

    public void setAnswertype12(String answertype12) {
        this.answertype12 = answertype12;
    }

    public void setAnswertype13(String answertype13) {
        this.answertype13 = answertype13;
    }

    public void setAnswertype14(String answertype14) {
        this.answertype14 = answertype14;
    }

    public void setAnswertype15(String answertype15) {
        this.answertype15 = answertype15;
    }

    public void setAnswertype16(String answertype16) {
        this.answertype16 = answertype16;
    }

    public void setAnswertype17(String answertype17) {
        this.answertype17 = answertype17;
    }

    public void setAnswertype18(String answertype18) {
        this.answertype18 = answertype18;
    }

    public void setAnswertype19(String answertype19) {
        this.answertype19 = answertype19;
    }

    public void setAnswertype20(String answertype20) {
        this.answertype20 = answertype20;
    }

    public void setAnswertype6(String answertype6) {
        this.answertype6 = answertype6;
    }

    public void setAnswertype7(String answertype7) {
        this.answertype7 = answertype7;
    }

    public void setAnswertype8(String answertype8) {
        this.answertype8 = answertype8;
    }

    public void setAnswertype9(String answertype9) {
        this.answertype9 = answertype9;
    }

    public void setEn_answer10(String en_answer10) {
        this.en_answer10 = en_answer10;
    }

    public void setEn_answer11(String en_answer11) {
        this.en_answer11 = en_answer11;
    }

    public void setEn_answer12(String en_answer12) {
        this.en_answer12 = en_answer12;
    }

    public void setEn_answer13(String en_answer13) {
        this.en_answer13 = en_answer13;
    }

    public void setEn_answer14(String en_answer14) {
        this.en_answer14 = en_answer14;
    }

    public void setEn_answer15(String en_answer15) {
        this.en_answer15 = en_answer15;
    }

    public void setEn_answer16(String en_answer16) {
        this.en_answer16 = en_answer16;
    }

    public void setEn_answer17(String en_answer17) {
        this.en_answer17 = en_answer17;
    }

    public void setEn_answer18(String en_answer18) {
        this.en_answer18 = en_answer18;
    }

    public void setEn_answer19(String en_answer19) {
        this.en_answer19 = en_answer19;
    }

    public void setEn_answer20(String en_answer20) {
        this.en_answer20 = en_answer20;
    }

    public void setEn_answer6(String en_answer6) {
        this.en_answer6 = en_answer6;
    }

    public void setEn_answer7(String en_answer7) {
        this.en_answer7 = en_answer7;
    }

    public void setEn_answer8(String en_answer8) {
        this.en_answer8 = en_answer8;
    }

    public void setEn_answer9(String en_answer9) {
        this.en_answer9 = en_answer9;
    }

    public void setEs_answer10(String es_answer10) {
        this.es_answer10 = es_answer10;
    }

    public void setEs_answer11(String es_answer11) {
        this.es_answer11 = es_answer11;
    }

    public void setEs_answer12(String es_answer12) {
        this.es_answer12 = es_answer12;
    }

    public void setEs_answer13(String es_answer13) {
        this.es_answer13 = es_answer13;
    }

    public void setEs_answer14(String es_answer14) {
        this.es_answer14 = es_answer14;
    }

    public void setEs_answer15(String es_answer15) {
        this.es_answer15 = es_answer15;
    }

    public void setEs_answer16(String es_answer16) {
        this.es_answer16 = es_answer16;
    }

    public void setEs_answer17(String es_answer17) {
        this.es_answer17 = es_answer17;
    }

    public void setEs_answer18(String es_answer18) {
        this.es_answer18 = es_answer18;
    }

    public void setEs_answer19(String es_answer19) {
        this.es_answer19 = es_answer19;
    }

    public void setEs_answer20(String es_answer20) {
        this.es_answer20 = es_answer20;
    }

    public void setEs_answer6(String es_answer6) {
        this.es_answer6 = es_answer6;
    }

    public void setEs_answer7(String es_answer7) {
        this.es_answer7 = es_answer7;
    }

    public void setEs_answer8(String es_answer8) {
        this.es_answer8 = es_answer8;
    }

    public void setEs_answer9(String es_answer9) {
        this.es_answer9 = es_answer9;
    }

    public void setKey10(String key10) {
        this.key10 = key10;
    }

    public void setKey11(String key11) {
        this.key11 = key11;
    }

    public void setKey12(String key12) {
        this.key12 = key12;
    }

    public void setKey13(String key13) {
        this.key13 = key13;
    }

    public void setKey14(String key14) {
        this.key14 = key14;
    }

    public void setKey15(String key15) {
        this.key15 = key15;
    }

    public void setKey16(String key16) {
        this.key16 = key16;
    }

    public void setKey17(String key17) {
        this.key17 = key17;
    }

    public void setKey18(String key18) {
        this.key18 = key18;
    }

    public void setKey19(String key19) {
        this.key19 = key19;
    }

    public void setKey20(String key20) {
        this.key20 = key20;
    }

    public void setKey6(String key6) {
        this.key6 = key6;
    }

    public void setKey7(String key7) {
        this.key7 = key7;
    }

    public void setKey8(String key8) {
        this.key8 = key8;
    }

    public void setKey9(String key9) {
        this.key9 = key9;
    }

    public void setPt_answer10(String pt_answer10) {
        this.pt_answer10 = pt_answer10;
    }

    public void setPt_answer11(String pt_answer11) {
        this.pt_answer11 = pt_answer11;
    }

    public void setPt_answer12(String pt_answer12) {
        this.pt_answer12 = pt_answer12;
    }

    public void setPt_answer13(String pt_answer13) {
        this.pt_answer13 = pt_answer13;
    }

    public void setPt_answer14(String pt_answer14) {
        this.pt_answer14 = pt_answer14;
    }

    public void setPt_answer15(String pt_answer15) {
        this.pt_answer15 = pt_answer15;
    }

    public void setPt_answer16(String pt_answer16) {
        this.pt_answer16 = pt_answer16;
    }

    public void setPt_answer17(String pt_answer17) {
        this.pt_answer17 = pt_answer17;
    }

    public void setPt_answer18(String pt_answer18) {
        this.pt_answer18 = pt_answer18;
    }

    public void setPt_answer19(String pt_answer19) {
        this.pt_answer19 = pt_answer19;
    }

    public void setPt_answer20(String pt_answer20) {
        this.pt_answer20 = pt_answer20;
    }

    public void setPt_answer6(String pt_answer6) {
        this.pt_answer6 = pt_answer6;
    }

    public void setPt_answer7(String pt_answer7) {
        this.pt_answer7 = pt_answer7;
    }

    public void setPt_answer8(String pt_answer8) {
        this.pt_answer8 = pt_answer8;
    }

    public void setPt_answer9(String pt_answer9) {
        this.pt_answer9 = pt_answer9;
    }

    public String getAnswer10() {
        return answer10;
    }

    public String getAnswer11() {
        return answer11;
    }

    public String getAnswer12() {
        return answer12;
    }

    public String getAnswer13() {
        return answer13;
    }

    public String getAnswer14() {
        return answer14;
    }

    public String getAnswer15() {
        return answer15;
    }

    public String getAnswer16() {
        return answer16;
    }

    public String getAnswer17() {
        return answer17;
    }

    public String getAnswer18() {
        return answer18;
    }

    public String getAnswer19() {
        return answer19;
    }

    public String getAnswer20() {
        return answer20;
    }

    public String getAnswer21() {
        return answer21;
    }

    public String getAnswer22() {
        return answer22;
    }

    public String getAnswer6() {
        return answer6;
    }

    public String getAnswer7() {
        return answer7;
    }

    public String getAnswer8() {
        return answer8;
    }

    public String getAnswer9() {
        return answer9;
    }

    public String getAnswertype21() {
        return answertype21;
    }

    public String getAnswertype22() {
        return answertype22;
    }

    public String getEn_answer21() {
        return en_answer21;
    }

    public String getEn_answer22() {
        return en_answer22;
    }

    public String getEs_answer21() {
        return es_answer21;
    }

    public String getEs_answer22() {
        return es_answer22;
    }

    public String getKey21() {
        return key21;
    }

    public String getKey22() {
        return key22;
    }

    public String getPt_answer21() {
        return pt_answer21;
    }

    public String getPt_answer22() {
        return pt_answer22;
    }

    public void setAnswer10(String answer10) {
        this.answer10 = answer10;
    }

    public void setAnswer11(String answer11) {
        this.answer11 = answer11;
    }

    public void setAnswer12(String answer12) {
        this.answer12 = answer12;
    }

    public void setAnswer13(String answer13) {
        this.answer13 = answer13;
    }

    public void setAnswer14(String answer14) {
        this.answer14 = answer14;
    }

    public void setAnswer15(String answer15) {
        this.answer15 = answer15;
    }

    public void setAnswer16(String answer16) {
        this.answer16 = answer16;
    }

    public void setAnswer17(String answer17) {
        this.answer17 = answer17;
    }

    public void setAnswer18(String answer18) {
        this.answer18 = answer18;
    }

    public void setAnswer19(String answer19) {
        this.answer19 = answer19;
    }

    public void setAnswer20(String answer20) {
        this.answer20 = answer20;
    }

    public void setAnswer21(String answer21) {
        this.answer21 = answer21;
    }

    public void setAnswer22(String answer22) {
        this.answer22 = answer22;
    }

    public void setAnswer6(String answer6) {
        this.answer6 = answer6;
    }

    public void setAnswer7(String answer7) {
        this.answer7 = answer7;
    }

    public void setAnswer8(String answer8) {
        this.answer8 = answer8;
    }

    public void setAnswer9(String answer9) {
        this.answer9 = answer9;
    }

    public void setAnswertype21(String answertype21) {
        this.answertype21 = answertype21;
    }

    public void setAnswertype22(String answertype22) {
        this.answertype22 = answertype22;
    }

    public void setEn_answer21(String en_answer21) {
        this.en_answer21 = en_answer21;
    }

    public void setEn_answer22(String en_answer22) {
        this.en_answer22 = en_answer22;
    }

    public void setEs_answer21(String es_answer21) {
        this.es_answer21 = es_answer21;
    }

    public void setEs_answer22(String es_answer22) {
        this.es_answer22 = es_answer22;
    }

    public void setKey21(String key21) {
        this.key21 = key21;
    }

    public void setKey22(String key22) {
        this.key22 = key22;
    }

    public void setPt_answer21(String pt_answer21) {
        this.pt_answer21 = pt_answer21;
    }

    public void setPt_answer22(String pt_answer22) {
        this.pt_answer22 = pt_answer22;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getAnswer23() {
        return answer23;
    }

    public String getAnswertype23() {
        return answertype23;
    }

    public String getEn_answer23() {
        return en_answer23;
    }

    public String getEs_answer23() {
        return es_answer23;
    }

    public String getKey23() {
        return key23;
    }

    public String getPt_answer23() {
        return pt_answer23;
    }

    public void setAnswer23(String answer23) {
        this.answer23 = answer23;
    }

    public void setAnswertype23(String answertype23) {
        this.answertype23 = answertype23;
    }

    public void setEn_answer23(String en_answer23) {
        this.en_answer23 = en_answer23;
    }

    public void setEs_answer23(String es_answer23) {
        this.es_answer23 = es_answer23;
    }

    public void setKey23(String key23) {
        this.key23 = key23;
    }

    public void setPt_answer23(String pt_answer23) {
        this.pt_answer23 = pt_answer23;
    }

	public String getAnswer24() {
		return answer24;
	}

	public void setAnswer24(String answer24) {
		this.answer24 = answer24;
	}

	public String getKey24() {
		return key24;
	}

	public void setKey24(String key24) {
		this.key24 = key24;
	}

	public String getEs_answer24() {
		return es_answer24;
	}

	public void setEs_answer24(String es_answer24) {
		this.es_answer24 = es_answer24;
	}

	public String getEn_answer24() {
		return en_answer24;
	}

	public void setEn_answer24(String en_answer24) {
		this.en_answer24 = en_answer24;
	}

	public String getPt_answer24() {
		return pt_answer24;
	}

	public void setPt_answer24(String pt_answer24) {
		this.pt_answer24 = pt_answer24;
	}

	public String getAnswer25() {
		return answer25;
	}

	public void setAnswer25(String answer25) {
		this.answer25 = answer25;
	}

	public String getKey25() {
		return key25;
	}

	public void setKey25(String key25) {
		this.key25 = key25;
	}

	public String getEs_answer25() {
		return es_answer25;
	}

	public void setEs_answer25(String es_answer25) {
		this.es_answer25 = es_answer25;
	}

	public String getEn_answer25() {
		return en_answer25;
	}

	public void setEn_answer25(String en_answer25) {
		this.en_answer25 = en_answer25;
	}

	public String getPt_answer25() {
		return pt_answer25;
	}

	public void setPt_answer25(String pt_answer25) {
		this.pt_answer25 = pt_answer25;
	}

	public String getAnswer26() {
		return answer26;
	}

	public void setAnswer26(String answer26) {
		this.answer26 = answer26;
	}

	public String getKey26() {
		return key26;
	}

	public void setKey26(String key26) {
		this.key26 = key26;
	}

	public String getEs_answer26() {
		return es_answer26;
	}

	public void setEs_answer26(String es_answer26) {
		this.es_answer26 = es_answer26;
	}

	public String getEn_answer26() {
		return en_answer26;
	}

	public void setEn_answer26(String en_answer26) {
		this.en_answer26 = en_answer26;
	}

	public String getPt_answer26() {
		return pt_answer26;
	}

	public void setPt_answer26(String pt_answer26) {
		this.pt_answer26 = pt_answer26;
	}

	public String getAnswer27() {
		return answer27;
	}

	public void setAnswer27(String answer27) {
		this.answer27 = answer27;
	}

	public String getKey27() {
		return key27;
	}

	public void setKey27(String key27) {
		this.key27 = key27;
	}

	public String getEs_answer27() {
		return es_answer27;
	}

	public void setEs_answer27(String es_answer27) {
		this.es_answer27 = es_answer27;
	}

	public String getEn_answer27() {
		return en_answer27;
	}

	public void setEn_answer27(String en_answer27) {
		this.en_answer27 = en_answer27;
	}

	public String getPt_answer27() {
		return pt_answer27;
	}

	public void setPt_answer27(String pt_answer27) {
		this.pt_answer27 = pt_answer27;
	}

	public String getAnswer28() {
		return answer28;
	}

	public void setAnswer28(String answer28) {
		this.answer28 = answer28;
	}

	public String getKey28() {
		return key28;
	}

	public void setKey28(String key28) {
		this.key28 = key28;
	}

	public String getEs_answer28() {
		return es_answer28;
	}

	public void setEs_answer28(String es_answer28) {
		this.es_answer28 = es_answer28;
	}

	public String getEn_answer28() {
		return en_answer28;
	}

	public void setEn_answer28(String en_answer28) {
		this.en_answer28 = en_answer28;
	}

	public String getPt_answer28() {
		return pt_answer28;
	}

	public void setPt_answer28(String pt_answer28) {
		this.pt_answer28 = pt_answer28;
	}

	public String getAnswer29() {
		return answer29;
	}

	public void setAnswer29(String answer29) {
		this.answer29 = answer29;
	}

	public String getKey29() {
		return key29;
	}

	public void setKey29(String key29) {
		this.key29 = key29;
	}

	public String getEs_answer29() {
		return es_answer29;
	}

	public void setEs_answer29(String es_answer29) {
		this.es_answer29 = es_answer29;
	}

	public String getEn_answer29() {
		return en_answer29;
	}

	public void setEn_answer29(String en_answer29) {
		this.en_answer29 = en_answer29;
	}

	public String getPt_answer29() {
		return pt_answer29;
	}

	public void setPt_answer29(String pt_answer29) {
		this.pt_answer29 = pt_answer29;
	}

	public String getAnswer30() {
		return answer30;
	}

	public void setAnswer30(String answer30) {
		this.answer30 = answer30;
	}

	public String getKey30() {
		return key30;
	}

	public void setKey30(String key30) {
		this.key30 = key30;
	}

	public String getEs_answer30() {
		return es_answer30;
	}

	public void setEs_answer30(String es_answer30) {
		this.es_answer30 = es_answer30;
	}

	public String getEn_answer30() {
		return en_answer30;
	}

	public void setEn_answer30(String en_answer30) {
		this.en_answer30 = en_answer30;
	}

	public String getPt_answer30() {
		return pt_answer30;
	}

	public void setPt_answer30(String pt_answer30) {
		this.pt_answer30 = pt_answer30;
	}

	public String getAnswertype24() {
		return answertype24;
	}

	public void setAnswertype24(String answertype24) {
		this.answertype24 = answertype24;
	}

	public String getAnswertype25() {
		return answertype25;
	}

	public void setAnswertype25(String answertype25) {
		this.answertype25 = answertype25;
	}

	public String getAnswertype26() {
		return answertype26;
	}

	public void setAnswertype26(String answertype26) {
		this.answertype26 = answertype26;
	}

	public String getAnswertype27() {
		return answertype27;
	}

	public void setAnswertype27(String answertype27) {
		this.answertype27 = answertype27;
	}

	public String getAnswertype28() {
		return answertype28;
	}

	public void setAnswertype28(String answertype28) {
		this.answertype28 = answertype28;
	}

	public String getAnswertype29() {
		return answertype29;
	}

	public void setAnswertype29(String answertype29) {
		this.answertype29 = answertype29;
	}

	public String getAnswertype30() {
		return answertype30;
	}

	public void setAnswertype30(String answertype30) {
		this.answertype30 = answertype30;
	}

	public String getWrt() {
		return wrt;
	}

	public void setWrt(String wrt) {
		this.wrt = wrt;
	}

	public String getEs_video() {
		return es_video;
	}

	public void setEs_video(String es_video) {
		this.es_video = es_video;
	}

	public String getEn_video() {
		return en_video;
	}

	public void setEn_video(String en_video) {
		this.en_video = en_video;
	}

	public String getPt_video() {
		return pt_video;
	}

	public void setPt_video(String pt_video) {
		this.pt_video = pt_video;
	}

	public FormFile getEs_videoFile() {
		return es_videoFile;
	}

	public void setEs_videoFile(FormFile es_videoFile) {
		this.es_videoFile = es_videoFile;
	}

	public FormFile getEn_videoFile() {
		return en_videoFile;
	}

	public void setEn_videoFile(FormFile en_videoFile) {
		this.en_videoFile = en_videoFile;
	}

	public FormFile getPt_videoFile() {
		return pt_videoFile;
	}

	public void setPt_videoFile(FormFile pt_videoFile) {
		this.pt_videoFile = pt_videoFile;
	}

	public String getEs_videoKey() {
		return es_videoKey;
	}

	public void setEs_videoKey(String es_videoKey) {
		this.es_videoKey = es_videoKey;
	}

	public String getEn_videoKey() {
		return en_videoKey;
	}

	public void setEn_videoKey(String en_videoKey) {
		this.en_videoKey = en_videoKey;
	}

	public String getPt_videoKey() {
		return pt_videoKey;
	}

	public void setPt_videoKey(String pt_videoKey) {
		this.pt_videoKey = pt_videoKey;
	}

}
