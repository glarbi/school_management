/**
 * 
 */
package org;

/**
 * @author larbi
 *
 */
public class REGISTRATION {
	public Integer idStudent;
	public Integer idTeacher;
	public Integer idSubject;
	public String date_reg;
	
	public REGISTRATION(Integer idStudent, Integer idTeacher, Integer idSubject, String date_reg) {
		super();
		this.idStudent = idStudent;
		this.idTeacher = idTeacher;
		this.idSubject = idSubject;
		this.date_reg = date_reg;
	}
}
