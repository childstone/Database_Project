package professor;
import mainFrame.mainGUI;

import java.awt.*; // AWT 패키지 가져오기
import java.awt.event.*; // 이벤트 관련 패키지 가져오기
import javax.swing.*; // 스윙 패키지 가져오기
import javax.swing.border.EmptyBorder; // 빈 테두리 관련 패키지 가져오기
import javax.swing.table.DefaultTableCellRenderer; // 테이블 셀 렌더러 관련 패키지 가져오기
import java.sql.*; // SQL 패키지 가져오기
import java.util.Calendar; // 캘린더 클래스 가져오기
import javax.swing.border.TitledBorder; // 테두리 관련 패키지 가져오기

public class professor_button2 extends JFrame {

    private JPanel contentPane; // 메인 콘텐츠 패널
    private JTextField searchField; // 검색 필드
    private JTable lectureTable; // 강의 테이블
    private JTextArea professorInfoArea; // 교수 정보 영역
    private Connection connection; // 데이터베이스 연결 객체
    private JLabel currentClassInfoLabel; // 현재 교시 수업 정보 라벨

    /**
     * 애플리케이션 실행 메서드
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() { // 이벤트 큐에서 실행
            public void run() {
                try {
                    professor_button2 frame = new professor_button2(); // 프레임 객체 생성
                    frame.setVisible(true); // 프레임 보이기
                } catch (Exception e) {
                    e.printStackTrace(); // 예외 발생 시 스택 트레이스 출력
                }
            }
        });
    }

    /**
     * 프레임 생성자
     */
    public professor_button2() {
        // 데이터베이스 연결 설정
        connectToDatabase();

        setBackground(new Color(255, 255, 255)); // 배경색 설정
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 닫기 버튼 설정
        setBounds(100, 100, 1100, 800); // 프레임 크기 설정
        setLocation(50, 50); // 프레임 위치 설정
        contentPane = new JPanel(); // 메인 콘텐츠 패널 생성
        contentPane.setForeground(new Color(255, 255, 255)); // 전경색 설정
        contentPane.setBackground(new Color(255, 255, 255)); // 배경색 설정
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5)); // 테두리 설정
        contentPane.setLayout(new BorderLayout(10, 10)); // 레이아웃 설정
        setContentPane(contentPane); // 프레임에 콘텐츠 패널 추가

        // 헤더 패널 생성
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(255, 255, 255)); // 배경색 설정
        headerPanel.setLayout(new BorderLayout()); // 레이아웃 설정

        // 로고 패널 생성
        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(new Color(255, 255, 255)); // 배경색 설정
        logoPanel.setLayout(new GridLayout(2, 0, 0, 0)); // 그리드 레이아웃 설정
        logoPanel.setPreferredSize(new Dimension(100, 100)); // 선호 크기 설정

        JLabel logo1 = new JLabel("공간이 필요하다면?"); // 로고 텍스트1
        logo1.setFont(new Font("맑은 고딕", Font.PLAIN, 20)); // 폰트 설정
        logo1.setHorizontalAlignment(SwingConstants.CENTER); // 수평 정렬 설정
        logo1.setVerticalAlignment(SwingConstants.BOTTOM); // 수직 정렬 설정
        logoPanel.add(logo1); // 로고 패널에 추가

        JLabel logo2 = new JLabel("Gong-Gang"); // 로고 텍스트2
        logo2.setHorizontalAlignment(SwingConstants.CENTER); // 수평 정렬 설정
        logo2.setFont(new Font("Arial Black", Font.BOLD, 40)); // 폰트 설정
        logoPanel.add(logo2); // 로고 패널에 추가

        headerPanel.add(logoPanel, BorderLayout.NORTH); // 헤더 패널에 로고 패널 추가

        // 검색 패널 생성
        JPanel searchPanel = new JPanel();
        searchPanel.setBackground(new Color(255, 255, 255)); // 배경색 설정
        searchPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20)); // 플로우 레이아웃 설정

        JLabel searchLabel = new JLabel("교수님 이름:"); // 검색 라벨
        searchLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 20)); // 폰트 설정
        searchPanel.add(searchLabel); // 검색 패널에 추가

        searchField = new JTextField(); // 검색 필드 생성
        searchField.setFont(new Font("맑은 고딕", Font.PLAIN, 20)); // 폰트 설정
        searchField.setColumns(20); // 칼럼 수 설정
        searchPanel.add(searchField); // 검색 패널에 추가

        JButton searchButton = new JButton("검색"); // 검색 버튼 생성
        searchButton.setFont(new Font("맑은 고딕", Font.PLAIN, 20)); // 폰트 설정
        searchButton.addActionListener(new ActionListener() { // 검색 버튼 클릭 이벤트 리스너 추가
            public void actionPerformed(ActionEvent e) {
                searchProfessor(); // 교수 검색 메서드 호출
            }
        });
        searchPanel.add(searchButton); // 검색 패널에 검색 버튼 추가

        headerPanel.add(searchPanel, BorderLayout.SOUTH); // 헤더 패널에 검색 패널 추가

        contentPane.add(headerPanel, BorderLayout.NORTH); // 메인 패널에 헤더 패널 추가

        // 홈 버튼을 위한 홈 패널 생성
        JPanel homeButtonPanel = new JPanel();
        homeButtonPanel.setBackground(new Color(255, 255, 255)); // 배경 색 설정
        homeButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT)); // 홈 패널 플로우 레이아웃 설정
        contentPane.add(homeButtonPanel, BorderLayout.SOUTH); // 메인 패널에 홈 패널 추가

        JButton homeButton = new JButton("HOME"); // 홈 버튼 생성
        homeButton.setFont(new Font("Arial ExtraBold", Font.PLAIN, 12));
        homeButton.setBackground(new Color(255, 255, 255)); // 배경 색 설정
        homeButtonPanel.add(homeButton); // 홈 패널에 홈 버튼 추가

        // 홈 버튼에 ActionListener 추가
        homeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // 현재 프레임 닫기
                mainGUI mainFrame = new mainGUI();
                mainFrame.setVisible(true); // mainGUI 프레임 보이기
            }
        });

        // 결과 영역 (강의 테이블)
        String[] columnNames = { "", "월", "화", "수", "목", "금" }; // 테이블 컬럼 이름 설정
        String[][] data = new String[9][6]; // 테이블 데이터 배열 생성, 9교시 기준
        for (int i = 0; i < 9; i++) {
            data[i][0] = String.valueOf(i + 1); // 교시 번호 설정
        }
        lectureTable = new JTable(data, columnNames); // 테이블 생성
        lectureTable.setBounds(100, 100, 1100, 800);
        lectureTable.setFont(new Font("맑은 고딕", Font.PLAIN, 20)); // 폰트 설정
        lectureTable.setRowHeight(30); // 행 높이 설정
        JScrollPane scrollPane = new JScrollPane(lectureTable); // 스크롤 패널 생성
        contentPane.add(scrollPane, BorderLayout.CENTER); // 메인 패널에 스크롤 패널 추가

        // 강의 테이블 아래에 현재 교시 수업 정보를 표시하는 패널 생성
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(new TitledBorder("현재 교시 정보"));

        // 현재 교시 수업 정보를 표시하는 라벨 생성
        currentClassInfoLabel = new JLabel("현재 교시 정보가 여기에 표시됩니다.");
        currentClassInfoLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
        currentClassInfoLabel.setHorizontalAlignment(SwingConstants.LEFT); // 왼쪽 정렬 설정

        // infoPanel에 라벨 추가
        infoPanel.add(currentClassInfoLabel, BorderLayout.WEST);

        // 기존 scrollPane 아래에 infoPanel 추가
        contentPane.add(infoPanel, BorderLayout.WEST);
    }

    // 데이터베이스 연결 설정 메서드
    private void connectToDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // MySQL 드라이버 로드
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/DB2024Team05", "root", "root"); // 데이터베이스 연결
            System.out.println("Database connected successfully."); // 연결 성공 메시지 출력
        } catch (Exception e) {
            e.printStackTrace(); // 예외 발생 시 스택 트레이스 출력
        }
    }

    // 교수 검색 메서드
    private void searchProfessor() {
        String professorName = searchField.getText().trim(); // 검색어 가져오기
        if (professorName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "교수님 이름을 입력하세요.", "경고", JOptionPane.WARNING_MESSAGE); // 검색어 없을 시 경고 메시지
            return;
        }

        try {
            ProfessorInfo professorInfo = getProfessorInfo(professorName); // 교수 정보 가져오기
            if (professorInfo != null) {
                updateLectureTable(professorName); // 테이블 업데이트
            } else {
                JOptionPane.showMessageDialog(this, "해당 교수님의 정보를 찾을 수 없습니다.", "경고", JOptionPane.WARNING_MESSAGE); // 교수 정보 없을 시 경고 메시지
            }
        } catch (SQLException e) {
            e.printStackTrace(); // SQL 예외 발생 시 스택 트레이스 출력
        }
    }

    // 교수 정보 가져오기 메서드
    private ProfessorInfo getProfessorInfo(String professorName) throws SQLException {
        ProfessorInfo professorInfo = null;

        String professorQuery = "SELECT Name, Lab_Location, Phone, Email FROM DB2024_Professor WHERE Name = ?"; // 교수 정보 쿼리
        PreparedStatement professorStmt = connection.prepareStatement(professorQuery); // 쿼리 준비
        professorStmt.setString(1, professorName); // 검색어 설정
        ResultSet professorRs = professorStmt.executeQuery(); // 쿼리 실행

        if (professorRs.next()) {
            professorInfo = new ProfessorInfo(); // 교수 정보 객체 생성
            professorInfo.setName(professorRs.getString("Name")); // 이름 설정
        }

        return professorInfo; // 교수 정보 반환
    }

    // 강의 테이블 업데이트 메서드
    private void updateLectureTable(String professorName) {
        // 테이블 초기화
        for (int i = 0; i < 9; i++) {
            for (int j = 1; j < 6; j++) {
                lectureTable.setValueAt("", i, j); // 테이블 셀 초기화
            }
        }

        try {
            String lectureQuery = "SELECT Lecture_Name, Lecture_Time1, Lecture_Time2 FROM DB2024_Lecture WHERE Professor_Name = ?"; // 강의 정보를 가져오는 쿼리
            PreparedStatement lectureStmt = connection.prepareStatement(lectureQuery); // 쿼리 준비
            lectureStmt.setString(1, professorName); // 쿼리에 교수 이름 설정
            ResultSet lectureRs = lectureStmt.executeQuery(); // 쿼리 실행 및 결과 저장

            while (lectureRs.next()) { // 결과 집합 순회
                String lectureName = lectureRs.getString("Lecture_Name"); // 강의 이름 가져오기
                String lectureTime1 = lectureRs.getString("Lecture_Time1"); // 강의 시간1 가져오기
                String lectureTime2 = lectureRs.getString("Lecture_Time2"); // 강의 시간2 가져오기

                int dayIndex = getDayIndex(lectureTime1); // 강의 시간1의 요일 인덱스 가져오기
                if (dayIndex != -1) { // 유효한 요일 인덱스일 경우
                    int period = Integer.parseInt(lectureTime1.substring(1)) - 1; // 강의 시간1의 교시 계산
                    lectureTable.setValueAt(lectureName, period, dayIndex); // 테이블 셀에 강의 이름 설정
                }

                if (lectureTime2 != null) { // 강의 시간2가 null이 아닌 경우
                    dayIndex = getDayIndex(lectureTime2); // 강의 시간2의 요일 인덱스 가져오기
                    if (dayIndex != -1) { // 유효한 요일 인덱스일 경우
                        int period = Integer.parseInt(lectureTime2.substring(1)) - 1; // 강의 시간2의 교시 계산
                        lectureTable.setValueAt(lectureName, period, dayIndex); // 테이블 셀에 강의 이름 설정
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // SQL 예외 발생 시 스택 트레이스 출력
        }

        // 현재 요일과 교시 강조 표시
        highlightCurrentDayAndPeriod(); // 현재 요일과 교시 강조 표시 메서드 호출
    }

    // 요일 인덱스 가져오기 메서드
    private int getDayIndex(String time) {
        switch (time.charAt(0)) { // 시간 문자열의 첫 글자로 요일 인덱스 반환
            case '월':
                return 1;
            case '화':
                return 2;
            case '수':
                return 3;
            case '목':
                return 4;
            case '금':
                return 5;
            default:
                return -1; // 유효하지 않은 경우 -1 반환
        }
    }

    // 현재 요일과 교시 강조 표시 메서드
    private void highlightCurrentDayAndPeriod() {
        Calendar calendar = Calendar.getInstance(); // 현재 날짜와 시간을 가져오는 캘린더 인스턴스 생성
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK); // 현재 요일 가져오기
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY); // 현재 시간 가져오기

        int currentDayIndex = -1; // 현재 요일 인덱스 초기화
        switch (dayOfWeek) { // 요일에 따른 인덱스 설정
            case Calendar.MONDAY:
                currentDayIndex = 1;
                break;
            case Calendar.TUESDAY:
                currentDayIndex = 2;
                break;
            case Calendar.WEDNESDAY:
                currentDayIndex = 3;
                break;
            case Calendar.THURSDAY:
                currentDayIndex = 4;
                break;
            case Calendar.FRIDAY:
                currentDayIndex = 5;
                break;
        }

        int currentPeriod = -1; // 현재 교시 인덱스 초기화
        if (hourOfDay >= 8 && hourOfDay < 21) { // 8시부터 21시까지 시간 범위 확인
            currentPeriod = (int) Math.floor((hourOfDay - 8) / 1.5); // 8시부터 1.5시간 단위 교시 계산
        }

        // 새로운 렌더러 적용
        for (int i = 0; i < lectureTable.getRowCount(); i++) {
            for (int j = 0; j < lectureTable.getColumnCount(); j++) {
                lectureTable.getColumnModel().getColumn(j).setCellRenderer(new DefaultTableCellRenderer());
            }
        }

        String currentClassInfo = "수업 없음"; // 기본값을 "수업 없음"으로 설정

        if (currentDayIndex != -1) {
            lectureTable.getColumnModel().getColumn(currentDayIndex)
                    .setCellRenderer(new CustomRenderer(Color.PINK, -1));
        }
        if (currentPeriod != -1 && currentDayIndex != -1) {
            lectureTable.getColumnModel().getColumn(currentDayIndex)
                    .setCellRenderer(new CustomRenderer(Color.PINK, currentPeriod));
            lectureTable.getColumnModel().getColumn(currentDayIndex)
                    .setCellRenderer(new CustomRenderer(Color.RED, currentPeriod, currentDayIndex));

            // 현재 수업 정보를 업데이트
            Object currentClass = lectureTable.getValueAt(currentPeriod, currentDayIndex);
            if (currentClass != null && !currentClass.toString().isEmpty()) {
                currentClassInfo = "현재 수업: " + currentClass.toString() + ", 강의실 위치: "; // 강의실 위치를 추가해야 함
                try {
                    String roomQuery = "SELECT Room_Number FROM DB2024_Lecture WHERE Lecture_Name = ? AND (Lecture_Time1 = ? OR Lecture_Time2 = ?)";
                    PreparedStatement roomStmt = connection.prepareStatement(roomQuery);
                    roomStmt.setString(1, currentClass.toString());
                    roomStmt.setString(2, getTimeString(currentDayIndex, currentPeriod));
                    roomStmt.setString(3, getTimeString(currentDayIndex, currentPeriod));
                    ResultSet roomRs = roomStmt.executeQuery();
                    if (roomRs.next()) {
                        currentClassInfo += roomRs.getString("Room_Number");
                    } else {
                        currentClassInfo += "정보 없음";
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        currentClassInfoLabel.setText(currentClassInfo); // 현재 수업 정보를 라벨에 설정

        lectureTable.repaint();
    }

    // 요일과 교시를 문자열로 반환하는 헬퍼 메서드
    private String getTimeString(int dayIndex, int period) {
        String day = "";
        switch (dayIndex) {
            case 1:
                day = "월";
                break;
            case 2:
                day = "화";
                break;
            case 3:
                day = "수";
                break;
            case 4:
                day = "목";
                break;
            case 5:
                day = "금";
                break;
        }
        return day + (period + 1);
    }

    // 사용자 정의 렌더러 클래스
    class CustomRenderer extends DefaultTableCellRenderer {
        private Color backgroundColor; // 배경색
        private int highlightedRow = -1; // 강조할 행
        private int highlightedColumn = -1; // 강조할 열

        public CustomRenderer(Color backgroundColor) {
            this.backgroundColor = backgroundColor;
        }

        public CustomRenderer(Color backgroundColor, int highlightedRow) {
            this.backgroundColor = backgroundColor;
            this.highlightedRow = highlightedRow;
        }

        public CustomRenderer(Color backgroundColor, int highlightedRow, int highlightedColumn) {
            this.backgroundColor = backgroundColor;
            this.highlightedRow = highlightedRow;
            this.highlightedColumn = highlightedColumn;
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if ((highlightedRow == -1 || row == highlightedRow)
                    && (highlightedColumn == -1 || column == highlightedColumn)) {
                c.setBackground(backgroundColor); // 강조된 행과 열의 셀 배경색 설정
            } else if (column == highlightedColumn) {
                c.setBackground(Color.PINK); // 강조된 열의 셀 배경색 설정
            } else {
                c.setBackground(Color.WHITE); // 기본 셀 배경색 설정
            }
            return c;
        }
    }

    // 교수 정보 클래스
    class ProfessorInfo {
        private String name; // 교수 이름

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    // 강의 클래스
    class Lecture {
        private String lectureName; // 강의 이름
        private String lectureTime1; // 강의 시간1
        private String lectureTime2; // 강의 시간2

        public String getLectureName() {
            return lectureName;
        }

        public void setLectureName(String lectureName) {
            this.lectureName = lectureName;
        }

        public String getLectureTime1() {
            return lectureTime1;
        }

        public void setLectureTime1(String lectureTime1) {
            this.lectureTime1 = lectureTime1;
        }

        public String getLectureTime2() {
            return lectureTime2;
        }

        public void setLectureTime2(String lectureTime2) {
            this.lectureTime2 = lectureTime2;
        }
    }
}