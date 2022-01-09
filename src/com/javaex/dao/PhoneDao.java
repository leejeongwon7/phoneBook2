package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.javaex.vo.PersonVo;

public class PhoneDao {

//	필드
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "phonedb";
	private String pw = "phonedb";

//	생성자
	public PhoneDao() {
	}
//	메소드gs

//	메소드일반
	private void getConnection() {

		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName(driver);

			// 2. Connection 얻어오기
			conn = DriverManager.getConnection(url, id, pw);

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	} // getConn 종료

	private void close() {

		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

	} // close 종료

//	전화번호 추가	
	public void ContactsInput(PersonVo personVo) {

		getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행
//		String query = "insert into"; 쿼리문 만들기 -> ?주의
			String query = "";

//		tip 쿼리 에러를 피하기 위해 ""사이를 띄워서 공백을 넣어준다
			query += " INSERT INTO person ";

			query += " VALUES (seq_person_id.nextval, ?, ?, ?) ";
			System.out.println(query + " 추가 하는중");

//		문자열 쿼리문으로 만들기
			pstmt = conn.prepareStatement(query);

//		바인딩		
			pstmt.setString(1, personVo.getName());
			pstmt.setString(2, personVo.getHp());
			pstmt.setString(3, personVo.getCompany());

//		실행
			int count = pstmt.executeUpdate(); // 쿼리문 실행
//		4.결과처리
			System.out.println(count + "건이 저장되었습니다. \t (연락처) ");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		// 5. 자원정리
		close();

	} // 전화번호 추가 ContactsInput 종료

	// 전화번호 삭제
	public void ContactsRemove(int index) {

		getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " DELETE FROM person ";
			query += " WHERE person_id = ? ";
			System.out.println(query + "가 삭제 처리중");

//			문자열을 쿼리문으로 만들기
			pstmt = conn.prepareStatement(query);

//			바인딩
			pstmt.setInt(1, index);

//			실행
			int count = pstmt.executeUpdate();

//			4.결과처리
			System.out.println(index + "번 연락처가 삭제되었습니다.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

//		5.자원 닫기
		close();

	} // 연락처 삭제 ContactsRemove 종료

//	전화번호 수정 시작
	public void ContactsUpdate(PersonVo personVo) {

		getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " UPDATE person ";
			query += " SET	name = ?, ";
			query += " 		hp = ?, ";
			query += " 		company = ? ";
			query += " WHERE person_id = ? ";

			System.out.println(query);

//			문자열을 쿼리문으로 만들기
			pstmt = conn.prepareStatement(query);

//			바인딩
			pstmt.setString(1, personVo.getName());
			pstmt.setString(2, personVo.getHp());
			pstmt.setString(3, personVo.getCompany());
			pstmt.setInt(4, personVo.getPersonId());

//			실행
			int count = pstmt.executeUpdate();

			System.out.println(count + "건이 수정 되었습니다.");

			// 4.결과처리

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		// 5. 자원정리
		close();

	} // 전화번호 수정 ContactsUpdate 종료

// 전화번호 리스트 가져오기
	public List<PersonVo> PersonSelect() {
//	오라클db의 모든 작가 정보를 가져오는 코드 -> 리스트 출력

// 리스트 생성
		List<PersonVo> personList = new ArrayList<PersonVo>();

		getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			// 문자열 만들기
			String query = "";
			query += " 	SELECT  person_id pid, "; // author_id as id
			query += "          name, ";
			query += "          hp, ";
			query += "          company ";
			query += "  FROM person ";
			System.out.println(query);

			// 문자열 쿼리문으로 만들기
			pstmt = conn.prepareStatement(query);

			// 바인딩--> 생략 ( ? 없음)

			// 실행
			rs = pstmt.executeQuery();

			// 4.결과처리
			while (rs.next()) {

				int personId = rs.getInt("pid"); // 컬럼명이 id로 변경되었기 때문에
				String name = rs.getString("name");
				String hp = rs.getString("hp");
				String company = rs.getString("company");
				System.out.println(personId + ", " + name + ", " + hp + ", " + company + "\t" + " 출력");

			}

			// 출력

			for (int i = 0; i < personList.size(); i++) {
				PersonVo personVo = personList.get(i);
				System.out.println(personVo.getName() + ", " + personVo.getHp() + ", " + personVo.getCompany());
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		// 5. 자원정리
		close();

		return personList;

	} // 전화번호 리스트 PersonSelect 종료

	public List<PersonVo> personSearch() {
		List<PersonVo> personList = new ArrayList<PersonVo>();

		this.getConnection();

		Scanner sc = new Scanner(System.in); // 루프 오류방지 close 는 하지 않는다
		sc.nextLine(); // 개행문자 처리
		System.out.println("<5.검색>");
		System.out.print(">검색어: ");
		String search = sc.nextLine();

		try {
			String query = "";
			query += " select   person_id pid, ";
			query += "          name, ";
			query += "          hp, ";
			query += "          company ";
			query += " from person ";
			query += " where (name like ? or hp like ? or company like ?) ";

			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, "%" + search + "%");
			pstmt.setString(2, "%" + search + "%");
			pstmt.setString(3, "%" + search + "%");

			rs = pstmt.executeQuery();

			while (rs.next()) {
				int personId = rs.getInt("pid");
				String name = rs.getString("name");
				String hp = rs.getString("hp");
				String company = rs.getString("company");

				PersonVo vo = new PersonVo(personId, name, hp, company);
				personList.add(vo);
			}

			for (PersonVo pv : personList) {
				pv.showInfo();
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		this.close();

		return personList;
	} // personSearch 종료

//	겟리스트 데이터 (select 에서 출력 부분 빼버림)	셀렉터만 따로 메서드로 빼버림 
	public List<PersonVo> getList() {
		List<PersonVo> personList = new ArrayList<PersonVo>();

		this.getConnection();

		try {
			String query = "";
			query += " select   person_id pid, ";
			query += "          name, ";
			query += "          hp, ";
			query += "          company ";
			query += " from person ";
			query += " order by person_id desc ";

			pstmt = conn.prepareStatement(query);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				int personId = rs.getInt("pid");
				String name = rs.getString("name");
				String hp = rs.getString("hp");
				String company = rs.getString("company");

				PersonVo vo = new PersonVo(personId, name, hp, company);
				personList.add(vo);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		this.close();

		return personList;
	} // getList 종료

	public void printList() {

		for (PersonVo personvo : this.personSearch()) {
			personvo.showInfo();
		}
	} // printList 종료

	// 수정에서 쓸 (modify) getPerson
	public PersonVo getPerson(int index) {
		PersonVo personVo = new PersonVo();
		getConnection();

		try {
			String query = "";
			query += " select   person_id pid, ";
			query += " 			name, ";
			query += " 			hp, ";
			query += " 			company ";
			query += " from person ";
			query += " where person_id = ? ";

			System.out.println(query);

//			문자열을 쿼리문으로 만들기
			pstmt = conn.prepareStatement(query);

//			바인딩
			pstmt.setInt(1, index);

//			실행
			rs = pstmt.executeQuery();

			while (rs.next() == true) {
				int id = rs.getInt("pid");
				String name = rs.getString("name");
				String hp = rs.getString("hp");
				String company = rs.getString("company");

				personVo.setPersonId(id);
				personVo.setName(name);
				personVo.setHp(hp);
				personVo.setCompany(company);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

//		5.자원 닫기
		close();
		return personVo;

	} // getPerson 종료

}