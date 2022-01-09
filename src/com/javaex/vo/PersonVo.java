package com.javaex.vo;


public class PersonVo {

//필드 - 가져올 테이블 정보하고 일치하지 않는다 만들때 고민해볼것
	private int personId; // 아이디
	private String name; // 이름
	private String hp; // 핸드폰번호
	private String company; // 회사전번

//생성자 컨스트럭터
	public PersonVo() {
	}

	public PersonVo(int personId, String name, String hp, String company) {
		this.personId = personId;
		this.name = name;
		this.hp = hp;
		this.company = company;
	}

	public PersonVo(String name, String hp, String company) {
		this.name = name;
		this.hp = hp;
		this.company = company;
	}

//메서드 gs
	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHp() {
		return hp;
	}

	public void setHp(String hp) {
		this.hp = hp;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

//메서드 일반
	@Override
	public String toString() {
		return "PersonVo [personId=" + personId + ", name=" + name + ", hp=" + hp + ", company=" + company + "]";
	}

	public void showInfo() {
		System.out.println(name + ", " + hp + ", " + company);
	}

	public String save() {
		return name + "," + hp + "," + company;
	}
}