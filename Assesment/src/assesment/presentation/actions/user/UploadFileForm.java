/*
 * Created on 17-ago-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package assesment.presentation.actions.user;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

import assesment.communication.assesment.GroupData;
import assesment.persistence.util.Util;

public class UploadFileForm extends ActionForm {

    private static final long serialVersionUID = 1L;

    private String assessment;
    
	private FormFile file1;
	private FormFile file2;
	private FormFile file3;
	private FormFile file4;
	private FormFile file5;
	private FormFile file6;
	private FormFile file7;
	private FormFile file8;
	private FormFile file9;
	private FormFile file10;

    public UploadFileForm() {	    
	}

	public String getAssessment() {
		return assessment;
	}

	public void setAssessment(String assessment) {
		this.assessment = assessment;
	}

	public FormFile getFile1() {
		return file1;
	}

	public void setFile1(FormFile file1) {
		this.file1 = file1;
	}

	public FormFile getFile2() {
		return file2;
	}

	public void setFile2(FormFile file2) {
		this.file2 = file2;
	}

	public FormFile getFile3() {
		return file3;
	}

	public void setFile3(FormFile file3) {
		this.file3 = file3;
	}

	public FormFile getFile4() {
		return file4;
	}

	public void setFile4(FormFile file4) {
		this.file4 = file4;
	}

	public FormFile getFile5() {
		return file5;
	}

	public void setFile5(FormFile file5) {
		this.file5 = file5;
	}

	public FormFile getFile6() {
		return file6;
	}

	public void setFile6(FormFile file6) {
		this.file6 = file6;
	}

	public FormFile getFile7() {
		return file7;
	}

	public void setFile7(FormFile file7) {
		this.file7 = file7;
	}

	public FormFile getFile8() {
		return file8;
	}

	public void setFile8(FormFile file8) {
		this.file8 = file8;
	}

	public FormFile getFile9() {
		return file9;
	}

	public void setFile9(FormFile file9) {
		this.file9 = file9;
	}

	public FormFile getFile10() {
		return file10;
	}

	public void setFile10(FormFile file10) {
		this.file10 = file10;
	}

	public FormFile getFile(int i) {
		switch(i) {
			case 1:
				return file1;
			case 2:
				return file2;
			case 3:
				return file3;
			case 4:
				return file4;
			case 5:
				return file5;
			case 6:
				return file6;
			case 7:
				return file7;
			case 8:
				return file8;
			case 9:
				return file9;
			case 10:
				return file10;
		}
		return null;
	}
}