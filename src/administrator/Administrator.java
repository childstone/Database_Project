//관리자 코드 
package mainFrame;

import javax.swing.table.DefaultTableModel;

import mainFrame.mainGUI;

import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Vector;

/*
 * 데이터베이스 수정 및 관리 위한 관리자 코드
 *
 * [투플 값 관리] 추가/수정/삭제 선택
 * -> 5가지 테이블 중 관리할 테이블 선택
 * 추가 -> 데이터 입력받기
 * 수정 -> 수정할 데이터 선택
 * 삭제 -> 삭제할 데이터 선택
 *
 */

public class Administrator { // 메인 실행 함수
	public Administrator() {
		new 관리자();
	}
}

class 관리자 extends JFrame implements ActionListener, MouseListener {

	public JLabel result1 = new JLabel();
	public JTextField inLecture_Num = new JTextField(10); // 사용자 입력을 받기 위한 텍스트 필드 추가

	public JLabel result2 = new JLabel();
	public JTextField inClass_Num = new JTextField(10); // 사용자 입력을 받기 위한 텍스트 필드 추가

	public JLabel result3 = new JLabel();
	public JTextField inLecture_Name = new JTextField(30); // 사용자 입력을 받기 위한 텍스트 필드 추가

	public JLabel result4 = new JLabel();
	public JTextField inProfessor_Num = new JTextField(10); // 사용자 입력을 받기 위한 텍스트 필드 추가

	public JLabel result5 = new JLabel();
	public JTextField inProfessor_Name = new JTextField(10); // 사용자 입력을 받기 위한 텍스트 필드 추가

	public JLabel result6 = new JLabel();
	public JTextField inRoom_Number = new JTextField(10); // 사용자 입력을 받기 위한 텍스트 필드 추가

	public JLabel result7 = new JLabel();
	public JTextField inLecture_Time1 = new JTextField(10); // 사용자 입력을 받기 위한 텍스트 필드 추가

	public JLabel result8 = new JLabel();
	public JTextField inLecture_Time2 = new JTextField(10); // 사용자 입력을 받기 위한 텍스트 필드 추가

	// 수정 전 원본 값
	private String Room_Number_ori;
	private String Lecture_Time1_ori;
	private String Lecture_Time2_ori;
	private String Lecture_Num_ori;
	private String Class_Num_ori;

	Container ct = getContentPane(); // 메인 컨테이너 객체 생성

	JButton menu1_back = new JButton("취소"); // 첫 화면으로 돌아갈 뒤로가기 버튼 생성
	JButton input_back = new JButton("뒤로가기"); // 테이블을 관리 후에 돌아갈 뒤로가기 버튼 생성

	boolean AutoCommit_flag = true; // AutoCommit의 기본 세팅 값이 true
	JTable table;

	final int X = 200; // 배치를 위해 X와 Y값을 지정
	final int Y = 50; // Next = Y * n
	final int Y_gap = 40;

	// input Start Point
	final int XE = 50; // label Posision
	final int XE2 = 400; // input Field
	final int YE = 40; // Next = YE * n
	final int YE_gap = 40;
	JScrollPane scrollPane = null;
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql:/DB2024Team05";
	// Database credentials
	// MySQL 계정과 암호
	static final String USER = "DB2024Team05";
	static final String PASS = "DB2024Team05";

	public Connection conn;
	public Statement stmt;

	public void DatabaseManager() { // conn와 stmt를 매번 호출하지 않아도 실행되도록 함
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASS); // 데이터베이스 연결을 설정
			stmt = conn.createStatement(); // Statement 객체를 생성하여 SQL 문을 실행할 준비를 함
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public 관리자() { // 관리자 실행코드
		password();
	}

	public void password() { // 보안유지를 위해 MySQL 계정과 암호 입력하도록 함
		setTitle("ID Password Input");
		setSize(1100, 600);
		setLocation(50, 50);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ct.setLayout(new BorderLayout()); // Set layout to BorderLayout
		setVisible(true);

		JPanel logoPanel = new JPanel(); // 공강 로고를 넣을 패널
		logoPanel.setBackground(new Color(255, 255, 255));
		logoPanel.setLayout(new GridLayout(2, 0, 0, 0));
		logoPanel.setPreferredSize(new Dimension(100, 100)); // Set preferred size for the North panel
		ct.add(logoPanel, BorderLayout.NORTH); // 로고 패널 메인 컨테이너에 추가

		JLabel logo1 = new JLabel("Gong-Gang"); // 로고 레이블 생성
		logo1.setFont(new Font("Arial Black", Font.BOLD, 20));
		logo1.setHorizontalAlignment(SwingConstants.CENTER);
		logo1.setVerticalAlignment(SwingConstants.BOTTOM);
		logoPanel.add(logo1); // 로고 패널에 추가

		JLabel logo2 = new JLabel("Administrator LOGIN"); // 화면 창을 간단히 설명 하는 레이블 생성
		logo2.setHorizontalAlignment(SwingConstants.CENTER);
		logo2.setFont(new Font("Arial Black", Font.BOLD, 40));
		logoPanel.add(logo2); // 로고 패널에 추가

		// ID 입력
		Panel p1 = new Panel(); // 아이디관련 패널 생성
		p1.setLayout(new FlowLayout(FlowLayout.RIGHT)); // 패널 레이아웃 설정
		Label idLabel = new Label(String.format("%20s :", "ID")); // ID 입력 창임을 알려 주는 레이블
		p1.add(idLabel);
		TextField idField = new TextField(20); // 아이디 입력 받을 필드
		p1.add(idField);

		// password 입력
		Panel p2 = new Panel(); // 비밀번호관련 패널 생성
		p2.setLayout(new FlowLayout(FlowLayout.RIGHT));
		Label passLabel = new Label(String.format("%20s:", "PASSWORD")); // 비번 입력 창임을 알려 주는 레이블
		p2.add(passLabel);
		TextField passField = new TextField(20); // 비번 입력 받을 필드
		passField.setEchoChar('*'); // 비밀번호 입력 시 '*'로 표시
		p2.add(passField);

		// Enter 버튼
		JButton submitButton = new JButton("ENTER");
		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == submitButton) {
					String id = idField.getText(); // 입력 받은 id 저장
					String password = passField.getText(); // 입력 받은 password 저장

					System.out.println("ID: " + id);
					System.out.println("Password: " + password);

					if (id.equals(USER) && password.equals(PASS)) { // MySQL 계정과 암호가 입력한 내용과 같으면 메뉴를 호출
						DatabaseManager(); // 한번만 실행한다
						menu1();
					}
				}
			}
		});

		Panel p3 = new Panel();
		p3.setLayout(new FlowLayout(FlowLayout.LEFT));
		p3.add(submitButton);

		// Grid Center에 두기 위해 새로은 JPanel 생성
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(null); // 위치 커스텀을 위해 null
		centerPanel.setBackground(new Color(255, 255, 255));

		// 위치 조정
		p1.setBounds(X, Y * 1, 500, Y_gap); // x, y , Width, Height
		p2.setBounds(X, Y * 2, 500, Y_gap);
		p3.setBounds(500, Y * 3, 500, Y_gap);

		centerPanel.add(p1);
		centerPanel.add(p2);
		centerPanel.add(p3);

		// 홈 버튼
		JPanel homeButtonPanel = new JPanel(); // 홈 버튼 패널 생성
		homeButtonPanel.setBackground(new Color(255, 255, 255));
		ct.add(homeButtonPanel, BorderLayout.SOUTH); // 메인 컨테이너에 추가

		ct.add(centerPanel, BorderLayout.CENTER);
		JButton homeButton = new JButton("HOME"); // Home 화면으로 돌아가는 버튼
		homeButton.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 12));
		homeButton.setBackground(new Color(255, 255, 255));
		homeButtonPanel.add(homeButton); // 홈 버튼 패널에 추가
		homeButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		homeButton.addActionListener(this);

	}

	public void menu1() { // 투플의 삽입, 수정, 삭제를 위해 선택하는 메뉴
		ct.removeAll(); // 기존 컴포넌트 초기화
		revalidate();
		repaint();
		ct.setLayout(new BorderLayout()); // Set layout to BorderLayout

		// 로고 들어갈 패널 생성
		JPanel logoPanel = new JPanel();
		logoPanel.setBackground(new Color(255, 255, 255));
		logoPanel.setLayout(new BorderLayout());
		logoPanel.setPreferredSize(new Dimension(100, 100)); // Set preferred size for the North panel
		ct.add(logoPanel, BorderLayout.NORTH);

		// 강의 전체 보기 label
		JLabel logo1 = new JLabel("강의 전체 보기");
		logo1.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 20));
		logo1.setHorizontalAlignment(SwingConstants.CENTER);
		logo1.setVerticalAlignment(SwingConstants.BOTTOM);
		logoPanel.add(logo1, BorderLayout.WEST);

		// GONG-GANG 로고 label
		JLabel logo2 = new JLabel("Gong-Gang");
		logo2.setHorizontalAlignment(SwingConstants.CENTER);
		logo2.setFont(new Font("Arial Black", Font.BOLD, 40));
		logoPanel.add(logo2, BorderLayout.CENTER);

		// 강의 등록 버튼 패널 생성
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(new Color(255, 255, 255));
		logoPanel.add(buttonPanel, BorderLayout.EAST);

		JButton jb_insert = new JButton("강의등록");
		jb_insert.setBackground(new Color(255, 255, 255));
		buttonPanel.add(jb_insert);
		jb_insert.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 22));
		jb_insert.addActionListener(this);

		// 결과보기 영역 추가
		JPanel resultPanel = new JPanel();
		resultPanel.setBackground(new Color(255, 255, 255));
		resultPanel.setLayout(new BorderLayout());

		JScrollPane scrollPane = new JScrollPane(resultPanel);
		ct.add(scrollPane, BorderLayout.CENTER);

		결과보기(); // 결과보기 내용 호출

		// 홈 버튼 패널 생성
		JPanel homeButtonPanel = new JPanel();
		homeButtonPanel.setBackground(new Color(255, 255, 255));
		homeButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		ct.add(homeButtonPanel, BorderLayout.SOUTH);

		JButton homeButton = new JButton("HOME"); // Home 화면으로 가는 버튼
		homeButton.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 12));
		homeButton.setBackground(new Color(255, 255, 255));
		homeButtonPanel.add(homeButton);
		homeButton.addActionListener(this);

		setTitle("관리자");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 프레임을 닫을 때 프로그램이 종료
		setVisible(true);
	}

	// ActionEvent 클래스와 ActionListener 리스너 인터페이스를 사용해서 버튼 누르는 이벤트를 처리
	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		switch (command) {
		case "강의등록": // 투플 추가 로직 실행
			initInputVal();
			editWin(0);
			break;

		case "뒤로가기": // 뒤로가기 버튼을 눌렀을 때
			menu1(); // menu 화면으로 돌아감
			break;

		case "HOME": // 홈 버튼을 눌렀을 때
			dispose(); // Close current frame
			mainGUI mainFrame = new mainGUI();
			mainFrame.setVisible(true); // Open the mainGUI frame
			break;
		}
	}

	private void initInputVal() {
		inLecture_Num.setText(""); // 입력 필드 초기화
		inClass_Num.setText(""); // 입력 필드 초기화
		inLecture_Name.setText(""); // 입력 필드 초기화
		inProfessor_Num.setText(""); // 입력 필드 초기화
		inProfessor_Name.setText(""); // 입력 필드 초기화
		inRoom_Number.setText(""); // 입력 필드 초기화
		inLecture_Time1.setText(""); // 입력 필드 초기화
		inLecture_Time2.setText(""); // 입력 필드 초기화

	}

	private void editWin(int mode) { // 투플 삽입 로직 실행
		ct.removeAll(); // 기존 컴포넌트 초기화
		repaint();

		ct.setLayout(null);
		ct.setBackground(Color.white);

		// 로고 패널 생성
		JPanel logoPanel = new JPanel();
		logoPanel.setBackground(new Color(255, 255, 255));
		logoPanel.setLayout(new GridLayout(2, 0, 0, 0));
		logoPanel.setPreferredSize(new Dimension(100, 100)); // Set preferred size for the North panel
		ct.add(logoPanel);

		// 로고 생성
		JLabel logo1 = new JLabel("Gong-Gang");
		logo1.setFont(new Font("Arial Black", Font.BOLD, 20));
		logo1.setHorizontalAlignment(SwingConstants.CENTER);
		logo1.setVerticalAlignment(SwingConstants.BOTTOM);
		logoPanel.add(logo1);

		// insert 임을 알려주는 글 생성
		JLabel logo2;
		if (mode == 0) {
			logo2 = new JLabel("새로운 강의를 등록하세요");
		} else {
			logo2 = new JLabel("강의를 편집하세요");
		}
		logo2.setHorizontalAlignment(SwingConstants.CENTER);
		logo2.setFont(new Font("나눔고딕 ExtraBold", Font.BOLD, 40));
		logoPanel.add(logo2);

		result1.setText("학수번호");  result1.setHorizontalAlignment(JLabel.RIGHT);
		result2.setText("분반번호");	result2.setHorizontalAlignment(JLabel.RIGHT);
		result3.setText("강의이름");	result3.setHorizontalAlignment(JLabel.RIGHT);
		result4.setText("교수님번호");	result4.setHorizontalAlignment(JLabel.RIGHT);
		result5.setText("교수님이름");	result5.setHorizontalAlignment(JLabel.RIGHT);
		result6.setText("강의실");	result6.setHorizontalAlignment(JLabel.RIGHT);
		result7.setText("강의시간1");	result7.setHorizontalAlignment(JLabel.RIGHT);
		result8.setText("강의시간2");	result8.setHorizontalAlignment(JLabel.RIGHT);

		// 결과 텍스트와 입력 필드를 컨테이너에 추가
		ct.add(result1);
		ct.add(result2);
		ct.add(result3);
		ct.add(result4);
		ct.add(result5);
		ct.add(result6);
		ct.add(result7);
		ct.add(result8);

		ct.add(inLecture_Num);
		ct.add(inClass_Num);
		ct.add(inLecture_Name);
		ct.add(inProfessor_Num);
		ct.add(inProfessor_Name);
		ct.add(inRoom_Number);
		ct.add(inLecture_Time1);
		ct.add(inLecture_Time2);

		if (mode == 0) {
			JButton confirmButton = new JButton("등록");
			confirmButton.setBackground(new Color(255, 255, 255));
			confirmButton.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 15));
			confirmButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) { // 확인 버튼 누르면 실행
					insertTuple();
				}
			});
			ct.add(confirmButton);
			confirmButton.setBounds(XE2 + 200, YE * 10 + 100, 100, YE_gap);

			// 뒤로가기 버튼
			input_back.setBackground(new Color(255, 255, 255));
			input_back.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 15));
			input_back.addActionListener(this);
			ct.add(input_back);

			input_back.setBounds(XE2 - 50, YE * 10 + 100, 100, YE_gap);

		} else {
			// 수정
			JButton confirmButton = new JButton("수정");
			confirmButton.setBackground(new Color(255, 255, 255));
			confirmButton.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 15));
			confirmButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) { // 확인 버튼 누르면 실행
					updateTuple();
				}
			});
			ct.add(confirmButton);
			confirmButton.setBounds(XE2 + 100, YE * 10 + 100, 100, YE_gap);

			// 삭제
			JButton delButton = new JButton("삭제");
			delButton.setBackground(new Color(255, 255, 255));
			delButton.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 15));
			delButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) { // 확인 버튼 누르면 실행
					deleteTuple();
				}
			});
			ct.add(delButton);
			delButton.setBounds(XE2 + 200, YE * 10 + 100, 100, YE_gap);

			// 뒤로가기 버튼
			input_back.setBackground(new Color(255, 255, 255));
			input_back.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 15));
			input_back.addActionListener(this);

			ct.add(input_back);
			input_back.setBounds(XE2 - 50, YE * 10 + 100, 100, YE_gap);
		}

		logoPanel.setBounds(0, 0, 1100, 100);
		result1.setBounds(XE, YE * 1 + 100, 300, YE_gap);
		result2.setBounds(XE, YE * 2 + 100, 300, YE_gap);
		result3.setBounds(XE, YE * 3 + 100, 300, YE_gap);
		result4.setBounds(XE, YE * 4 + 100, 300, YE_gap);
		result5.setBounds(XE, YE * 5 + 100, 300, YE_gap);
		result6.setBounds(XE, YE * 6 + 100, 300, YE_gap);
		result7.setBounds(XE, YE * 7 + 100, 300, YE_gap);
		result8.setBounds(XE, YE * 8 + 100, 300, YE_gap);

		inLecture_Num.setBounds(XE2, YE * 1 + 100, 100, YE_gap);
		inClass_Num.setBounds(XE2, YE * 2 + 100, 100, YE_gap);
		inLecture_Name.setBounds(XE2, YE * 3 + 100, 200, YE_gap);
		inProfessor_Num.setBounds(XE2, YE * 4 + 100, 100, YE_gap);
		inProfessor_Name.setBounds(XE2, YE * 5 + 100, 100, YE_gap);
		inRoom_Number.setBounds(XE2, YE * 6 + 100, 100, YE_gap);
		inLecture_Time1.setBounds(XE2, YE * 7 + 100, 100, YE_gap);
		inLecture_Time2.setBounds(XE2, YE * 8 + 100, 100, YE_gap);

		ct.revalidate();
		ct.repaint();
	}

	private void insertTuple() {
		String Lecture_Num = inLecture_Num.getText();
		String Class_Num = inClass_Num.getText();
		String Lecture_Name = inLecture_Name.getText();
		String Professor_Num = inProfessor_Num.getText();
		String Professor_Name = inProfessor_Name.getText();
		String Room_Number = inRoom_Number.getText();
		String Lecture_Time1 = inLecture_Time1.getText();
		String Lecture_Time2 = inLecture_Time2.getText();

		String query1 = "INSERT INTO DB2024_Lecture (Lecture_Num,Class_Num,Lecture_Name,Professor_Num,Professor_Name,Room_Number,Lecture_Time1,Lecture_Time2)"
				+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		// Lecture_Num, Class_Num, Lecture_Name, Professor_Num, Professor_Name,
		String query2 = "INSERT INTO DB2024_Classroom_Schedule (Room_Number, Lecture_Time) VALUES (?, ?)";
		// 연결된 DB2024_Classroom_Schedule 테이블에서 튜플 추가하기
		String query3 = "INSERT INTO DB2024_Classroom_Schedule (Room_Number, Lecture_Time) VALUES (?, ?)";
		// 연결된 DB2024_Classroom_Schedule 테이블에서 튜플 추가하기

		try (PreparedStatement pStmt1 = conn.prepareStatement(query1);
				PreparedStatement pStmt2 = conn.prepareStatement(query2);
				PreparedStatement pStmt3 = conn.prepareStatement(query3);) {
			try {
				conn.setAutoCommit(false); // 트랜잭션 시작

				pStmt1.setInt(1, Integer.parseInt(Lecture_Num));
				pStmt1.setInt(2, Integer.parseInt(Class_Num));
				pStmt1.setString(3, Lecture_Name);
				pStmt1.setInt(4, Integer.parseInt(Professor_Num));
				pStmt1.setString(5, Professor_Name);
				pStmt1.setString(6, Room_Number);
				pStmt1.setString(7, Lecture_Time1);
				pStmt1.setString(8, Lecture_Time2);

				System.out.println("강의 정보 삽입");
				System.out.println(pStmt1); // 디버깅 위해 출력

				pStmt1.executeUpdate();

				try {
					pStmt2.setString(1, Room_Number);
					pStmt2.setString(2, Lecture_Time1);
					System.out.println(pStmt2); // 디버깅 위해 출력
					pStmt2.executeUpdate();
				} catch (SQLException se) {
					se.printStackTrace();
					System.out.println("강의일정1 추가 실패 -> Rolling back data");
					try {
						if (conn != null)
							conn.rollback(); // 실패 시 롤백
					} catch (SQLException se2) {
						se2.printStackTrace();
					} // end try
				}

				try {
					pStmt3.setString(1, Room_Number);
					pStmt3.setString(2, Lecture_Time2);
					System.out.println(pStmt3); // 디버깅 위해 출력
					pStmt3.executeUpdate();
				} catch (SQLException se) {
					se.printStackTrace();
					System.out.println("강의일정2 추가 실패 -> Rolling back data");
					try {
						if (conn != null)
							conn.rollback(); // 실패 시 롤백
					} catch (SQLException se2) {
						se2.printStackTrace();
					} // end try
				}
				initInputVal();

				conn.commit(); // 트랜잭션 진행되었다 
				System.out.println("transaction succeeds");

			} catch (SQLException se) {
				se.printStackTrace();
				System.out.println("Rolling back data here....");
				try {
					if (conn != null)
						conn.rollback(); // 실패 시 롤백
				} catch (SQLException se2) {
					se2.printStackTrace();
				} // end try
			}
			// 한 문장씩 디비에 반영되도록!
			conn.setAutoCommit(true);

		} catch (SQLException se) {
			se.printStackTrace();
		}
	}

	private void updateTuple() {
		String Lecture_Num = inLecture_Num.getText();
		String Class_Num = inClass_Num.getText();
		String Lecture_Name = inLecture_Name.getText();
		String Professor_Num = inProfessor_Num.getText();
		String Professor_Name = inProfessor_Name.getText();
		String Room_Number = inRoom_Number.getText();
		String Lecture_Time1 = inLecture_Time1.getText();
		String Lecture_Time2 = inLecture_Time2.getText();

		String query1 = "UPDATE DB2024_Lecture "
				+ "SET Lecture_Num = ?, Class_Num = ?, Lecture_Name = ?, Professor_Num = ?, Professor_Name = ?, Room_Number = ?, Lecture_Time1 = ?, Lecture_Time2 = ? "
				+ "WHERE Lecture_Num = ? AND Class_Num = ? ";
		// Lecture_Num, Class_Num, Lecture_Name, Professor_Num, Professor_Name,
		String query2 = "UPDATE DB2024_Classroom_Schedule " + "SET Room_Number = ?, Lecture_Time =?"
				+ "WHERE Room_Number = ? AND Lecture_Time = ?";
		// 연결된 DB2024_Classroom_Schedule 테이블에서 튜플 수정하기
		String query3 = "UPDATE DB2024_Classroom_Schedule " + "SET Room_Number = ?, Lecture_Time =?"
				+ "WHERE Room_Number = ? AND Lecture_Time = ?";
		// 연결된 DB2024_Classroom_Schedule 테이블에서 튜플 수정하기

		try (PreparedStatement pStmt1 = conn.prepareStatement(query1);
				PreparedStatement pStmt2 = conn.prepareStatement(query2);
				PreparedStatement pStmt3 = conn.prepareStatement(query3);) {
			try {
				conn.setAutoCommit(false); // 트랜잭션 시작

				pStmt1.setInt(1, Integer.parseInt(Lecture_Num));
				pStmt1.setInt(2, Integer.parseInt(Class_Num));
				pStmt1.setString(3, Lecture_Name);
				pStmt1.setInt(4, Integer.parseInt(Professor_Num));
				pStmt1.setString(5, Professor_Name);
				pStmt1.setString(6, Room_Number);
				pStmt1.setString(7, Lecture_Time1);
				pStmt1.setString(8, Lecture_Time2);
				pStmt1.setInt(9, Integer.parseInt(Lecture_Num_ori)); // 수정 전의 원본 값 사용
				pStmt1.setInt(10, Integer.parseInt(Class_Num_ori)); // 수정 전의 원본 값 사용

				System.out.println(pStmt1); // 디버깅 위해 출력

				pStmt1.executeUpdate();

				try {
					pStmt2.setString(1, Room_Number);
					pStmt2.setString(2, Lecture_Time1);
					pStmt2.setString(3, Room_Number_ori); // 원본 값을 사용
					pStmt2.setString(4, Lecture_Time1_ori); // 원본 값을 사용

					System.out.println(pStmt2); // 디버깅 위해 출력
					pStmt2.executeUpdate();
				} catch (SQLException se) {
					se.printStackTrace();
					System.out.println("강의일정1 수정 실패 -> Rolling back data");
					try {
						if (conn != null)
							conn.rollback(); // 실패 시 롤백
					} catch (SQLException se2) {
						se2.printStackTrace();
					} // end try
				}

				try {
					pStmt3.setString(1, Room_Number);
					pStmt3.setString(2, Lecture_Time2);
					pStmt3.setString(3, Room_Number_ori); // 원본 값을 사용
					pStmt3.setString(4, Lecture_Time2_ori); // 원본 값을 사용

					System.out.println(pStmt3); // 디버깅 위해 출력
					pStmt3.executeUpdate();
				} catch (SQLException se) {
					se.printStackTrace();
					System.out.println("강의일정2 수정 실패 -> Rolling back data");
					try {
						if (conn != null)
							conn.rollback(); // 실패 시 롤백
					} catch (SQLException se2) {
						se2.printStackTrace();
					} // end try
				}

				conn.commit(); // 트랜잭션 진행되었다 
				System.out.println("transaction succeeds");

			} catch (SQLException se) {
				se.printStackTrace();
				System.out.println("Rolling back data here....");
				try {
					if (conn != null)
						conn.rollback(); // 실패 시 롤백
				} catch (SQLException se2) {
					se2.printStackTrace();
				}
			}
			conn.setAutoCommit(true);

		} catch (SQLException se) {
			se.printStackTrace();
		}
	}

	private void deleteTuple() {
		String Lecture_Num = inLecture_Num.getText();
		String Class_Num = inClass_Num.getText();
		String Lecture_Name = inLecture_Name.getText();
		String Professor_Num = inProfessor_Num.getText();
		String Professor_Name = inProfessor_Name.getText();
		String Room_Number = inRoom_Number.getText();
		String Lecture_Time1 = inLecture_Time1.getText();
		String Lecture_Time2 = inLecture_Time2.getText();

		String query1 = "DELETE FROM DB2024_Lecture WHERE Lecture_Num = ? AND Class_Num = ?";
		// Lecture_Num, Class_Num, Lecture_Name, Professor_Num, Professor_Name,
		String query2 = "DELETE FROM DB2024_Classroom_Schedule WHERE Room_Number = ? AND Lecture_Time = ?";
		// 연결된 DB2024_Classroom_Schedule 테이블에서 삭제된 강의의 강의실 시간 정보도 삭제
		String query3 = "DELETE FROM DB2024_Classroom_Schedule WHERE Room_Number = ? AND Lecture_Time = ?";
		// 연결된 DB2024_Classroom_Schedule 테이블에서 삭제된 강의의 강의실 시간 정보도 삭제

		try (PreparedStatement pStmt1 = conn.prepareStatement(query1)) {

			conn.setAutoCommit(false); // 트랜잭션 시작

			// 첫 번째 쿼리 실행
			pStmt1.setInt(1, Integer.parseInt(Lecture_Num_ori));
			pStmt1.setInt(2, Integer.parseInt(Class_Num_ori));
			System.out.println(pStmt1);
			pStmt1.executeUpdate();

			// 두 번째 쿼리 실행
			try (PreparedStatement pStmt2 = conn.prepareStatement(query2)) {
				pStmt2.setString(1, Room_Number_ori);
				pStmt2.setString(2, Lecture_Time1_ori);
				System.out.println(pStmt2);
				pStmt2.executeUpdate();
			} catch (SQLException se) {
				se.printStackTrace();
				System.out.println("강의일정1 삭제 실패 -> Rolling back data");
				try {
					if (conn != null)
						conn.rollback(); // 실패 시 롤백
				} catch (SQLException se2) {
					se2.printStackTrace();
				} // end try
			}

			// 세 번째 쿼리 실행
			try (PreparedStatement pStmt3 = conn.prepareStatement(query3)) {
				pStmt3.setString(1, Room_Number_ori);
				pStmt3.setString(2, Lecture_Time2_ori); 
				pStmt3.executeUpdate();
				System.out.println(pStmt3);

			} catch (SQLException se) {
				se.printStackTrace();
				System.out.println("강의일정2 삭제 실패 -> Rolling back data");
				try {
					if (conn != null)
						conn.rollback(); // 실패 시 롤백
				} catch (SQLException se2) {
					se2.printStackTrace();
				} // end try
			}

			initInputVal(); // 입력필드 초기화

			conn.commit(); // 트랜잭션 진행되었다 
			System.out.println("transaction succeeds");


		} catch (SQLException se) {
			se.printStackTrace();
			System.out.println("Rolling back data here....");
			try {
				if (conn != null)
					conn.rollback(); // 실패 시 롤백
			} catch (SQLException se2) {
				se2.printStackTrace();
			} // end try
		} finally {
			// Auto-commit 설정을 원래대로 돌림
			try {
				conn.setAutoCommit(true);
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
	}

	public void 결과보기() { // 선택한 테이블 이름을 매개변수로 받아 해당 테이블을 보여주는 함수

		String query2 = "SELECT * FROM DB2024_Lecture";
		System.out.println(query2); // 디버깅을 위해 출력

		// JTable에 데이터를 넣기 위한 기본 테이블 모델을 생성합니다.
		DefaultTableModel tableModel = new DefaultTableModel();

		try (Statement stmt1 = conn.createStatement(); ResultSet rs = stmt1.executeQuery(query2)) {

			// ResultSet의 메타데이터를 가져오기
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount(); // 열의 수를 가져오기
			Vector<String> columnNames = new Vector<>();

			// 모든 열 이름을 가져와 벡터에 추가
			for (int column = 1; column <= columnCount; column++) {
				columnNames.add(metaData.getColumnName(column));
			}

			// Add column names to table model
			tableModel.setColumnIdentifiers(columnNames);

			// Get row data from ResultSet
			while (rs.next()) {
				Vector<Object> rowData = new Vector<>();
				for (int column = 1; column <= columnCount; column++) {
					rowData.add(rs.getObject(column));
				}
				tableModel.addRow(rowData);
			}

		} catch (SQLException se) {
			se.printStackTrace();
		}

		// Create JTable with the table model
		JTable table = new JTable(tableModel);
		this.table = table;

		scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		scrollPane.setAutoscrolls(true);

		Dimension size = ct.getSize();
		System.out.println("W:" + size.width + " H:" + size.height); // 디버깅을 위해 출력
		scrollPane.setBounds(10, 200, size.width - 20, size.height - 200);

		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setAutoscrolls(true);

		// resize event -> resize ScrollPane
		ct.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) { // 마우스가 가르키는 테이블의 값을 입력값으로 만드는 함수
				if (scrollPane != null) {
					Dimension size = ct.getSize();
					System.out.println("W:" + size.width + " H:" + size.height); // 디버깅을 위해 출력
					scrollPane.setBounds(10, 200, size.width - 40, size.height - 300);
					scrollPane.revalidate();
					scrollPane.repaint();

					ct.revalidate();
					ct.repaint();
				}
			}

		});
		table.addMouseListener(this); // --> mouseClicked

		ct.add(scrollPane, FlowLayout.CENTER);

		scrollPane.setVisible(true);
		ct.revalidate();
		ct.repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int row = table.getSelectedRow();
		int col = table.getSelectedColumn();
		String ret = "";
		String result = "";
		String sel = "";

		for (int i = 0; i < table.getColumnCount(); i++) {
			ret += table.getColumnName(i) + "=" + table.getModel().getValueAt(row, i);
			if (i < (table.getColumnCount() - 1))
				ret += ",";

			result += table.getModel().getValueAt(row, i);

			// set inputField
			switch (table.getColumnName(i)) {
			case "Lecture_Num":
				inLecture_Num.setText(result);
				Lecture_Num_ori = result;
				break;
			case "Class_Num":
				inClass_Num.setText(result);
				Class_Num_ori = result;
				break;
			case "Lecture_Name":
				inLecture_Name.setText(result);
				break;
			case "Professor_Num":
				inProfessor_Num.setText(result);
				break;
			case "Professor_Name":
				inProfessor_Name.setText(result);
				break;
			case "Room_Number":
				inRoom_Number.setText(result);
				Room_Number_ori = result; // 원본 값 저장
				break;
			case "Lecture_Time1":
				inLecture_Time1.setText(result);
				Lecture_Time1_ori = result; // 원본 값 저장
				break;
			case "Lecture_Time2":
				inLecture_Time2.setText(result);
				Lecture_Time2_ori = result; // 원본 값 저장
				break;
			}
			result = "";

		}
		editWin(1);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// 마우스 버튼이 눌렸을 때의 이벤트를 처리
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// 마우스 버튼이 떼어졌을 때의 이벤트를 처리
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// 마우스가 컴포넌트에 들어갔을 때의 이벤트를 처리
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// 마우스가 컴포넌트를 벗어났을 때의 이벤트를 처리
	}

} // end main
