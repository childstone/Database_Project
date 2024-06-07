package professor;

import java.awt.BorderLayout; // 레이아웃 관련 패키지 가져오기
import java.awt.Color; // 색상 관련 패키지 가져오기
import java.awt.Component; // 컴포넌트 관련 패키지 가져오기
import java.awt.Dimension; // 크기 관련 패키지 가져오기
import java.awt.Font; // 폰트 관련 패키지 가져오기
import java.awt.GridLayout; // 그리드 레이아웃 관련 패키지 가져오기
import java.awt.event.ActionEvent; // 이벤트 관련 패키지 가져오기
import java.awt.event.ActionListener; // 이벤트 리스너 관련 패키지 가져오기
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.Statement;
import java.sql.Connection; // 데이터베이스 연결 관련 패키지 가져오기
import java.sql.DriverManager; // 드라이버 관리자 관련 패키지 가져오기
import java.sql.PreparedStatement; // 준비된 명령문 관련 패키지 가져오기
import java.sql.ResultSet; // 결과 집합 관련 패키지 가져오기
import java.sql.SQLException; // SQL 예외 관련 패키지 가져오기
import java.util.ArrayList;
import java.util.HashMap; // 해시맵 클래스 가져오기
import java.util.Map; // 맵 인터페이스 가져오기

import javax.swing.JFrame; // JFrame 클래스 가져오기
import javax.swing.JLabel; // JLabel 클래스 가져오기
import javax.swing.JPanel; // JPanel 클래스 가져오기
import javax.swing.JScrollPane; // JScrollPane 클래스 가져오기
import javax.swing.JTextArea; // JTextArea 클래스 가져오기
import javax.swing.SwingConstants; // 스윙 상수 가져오기
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder; // 빈 테두리 관련 패키지 가져오기

import mainFrame.mainGUI; // mainGUI 클래스 가져오기

import javax.swing.JComboBox; // JComboBox 클래스 가져오기
import java.awt.FlowLayout; // 플로우 레이아웃 관련 패키지 가져오기

import javax.swing.BorderFactory; // 테두리 팩토리 관련 패키지 가져오기
import javax.swing.JButton; // JButton 클래스 가져오기
import javax.swing.JCheckBox; // JCheckBox 클래스 가져오기

public class professor_button1 extends JFrame {

    private JPanel contentPane; // 메인 콘텐츠 패널

    String usage; // 공간 유형
    String seats; // 좌석 수
    String cameraType; // 카메라 유형
    boolean content; // 콘센트 유무
    boolean project; // 빔프로젝트 유무
    boolean reservation; // 예약 필요 여부
    boolean recording; // 녹화 가능 여부
    boolean practicable; // 실습 가능 여부
    Map<String, Boolean> timeDictionary = new HashMap<>(); // 시간 선택 상태 저장할 맵
    ArrayList<JCheckBox> checkBoxList = new ArrayList<>();
    private JTextArea infoArea; // 정보 표시 영역
    String message = null;

    public professor_button1() {


        setTitle("GONG-GANG"); // 프레임 제목 설정
        setBackground(new Color(255, 255, 255)); // 배경색 설정
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 닫기 버튼 설정
        setBounds(100, 100, 1100, 600); // 프레임 크기 설정
        setLocation(50, 50); // 프레임 위치 설정


        contentPane = new JPanel(); // 메인 콘텐츠 패널 생성
        contentPane.setForeground(new Color(255, 255, 255)); // 전경색 설정
        contentPane.setBackground(new Color(255, 255, 255)); // 배경색 설정
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5)); // 테두리 설정
        contentPane.setLayout(new BorderLayout(10, 10)); // 레이아웃 설정
        setContentPane(contentPane); // 프레임에 콘텐츠 패널 추가

        // 로고 붙이는 Panel
        JPanel logoPanel = new JPanel(); // 로고 패널 생성
        logoPanel.setBackground(new Color(255, 255, 255)); // 배경색 설정
        logoPanel.setPreferredSize(new Dimension(1100, 103)); // 패널 선호 크기 설정
        contentPane.add(logoPanel, BorderLayout.NORTH); // 콘텐츠 패널에 로고 패널 추가
        logoPanel.setLayout(new GridLayout(2, 0, 0, 0)); // 그리드 레이아웃 설정

        // GONG-GANG 로고 label 생성
        JLabel logo = new JLabel("Gong-Gang"); // 로고 라벨 생성
        logo.setBackground(new Color(255, 255, 255)); // 배경색 설정
        logo.setHorizontalAlignment(SwingConstants.CENTER); // 수평 정렬 설정
        logo.setFont(new Font("Arial Black", Font.BOLD, 40)); // 폰트 설정
        logoPanel.add(logo); // 로고 패널에 로고 라벨 추가

        JLabel userLabel = new JLabel("- \uC6D0\uD558\uB294 \uAC15\uC758\uC2E4 \uCC3E\uAE30 -"); // 부제 라벨 생성
        userLabel.setHorizontalAlignment(SwingConstants.CENTER); // 수평 정렬 설정
        userLabel.setFont(new Font("나눔고딕", Font.BOLD, 22)); // 폰트 설정
        logoPanel.add(userLabel); // 로고 패널에 부제 라벨 추가

        JPanel mainPanel = new JPanel(); // 메인 패널 생성
        mainPanel.setBackground(new Color(255, 255, 255)); // 배경색 설정
        contentPane.add(mainPanel, BorderLayout.CENTER); // 콘텐츠 패널에 메인 패널 추가
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 20));

        //combobox와 checkbox 붙이는 subMainPanel
        JPanel subMainPanel = new JPanel();
        subMainPanel.setBackground(new Color(255, 255, 255));
        mainPanel.add(subMainPanel);
        subMainPanel.setLayout(new GridLayout(2, 0, 10, 20));
        subMainPanel.setPreferredSize(new Dimension(400, 170));

        //checkBox Panel
        JPanel checkBoxPanel = new JPanel();
        checkBoxPanel.setBackground(new Color(255, 255, 255));
        subMainPanel.add(checkBoxPanel);


        //checkBox 생성
        JCheckBox contentCheckBox = new JCheckBox("콘센트");
        contentCheckBox.setHorizontalAlignment(SwingConstants.LEFT);
        contentCheckBox.setFont(new Font("나눔고딕", Font.BOLD, 13));
        contentCheckBox.setBackground(new Color(255, 255, 255));
        JCheckBox beamProjectCheckBox = new JCheckBox("빔프로젝트");
        beamProjectCheckBox.setFont(new Font("나눔고딕", Font.BOLD, 13));
        beamProjectCheckBox.setBackground(new Color(255, 255, 255));
        JCheckBox reservationCheckBox = new JCheckBox("예약 필요");
        reservationCheckBox.setHorizontalAlignment(SwingConstants.LEFT);
        reservationCheckBox.setFont(new Font("나눔고딕", Font.BOLD, 13));
        reservationCheckBox.setBackground(new Color(255, 255, 255));
        JCheckBox recordingCheckBox = new JCheckBox("녹화 가능");
        recordingCheckBox.setHorizontalAlignment(SwingConstants.LEFT);
        recordingCheckBox.setFont(new Font("나눔고딕", Font.BOLD, 13));
        recordingCheckBox.setBackground(new Color(255, 255, 255));
        JCheckBox particableCheckBox=new JCheckBox("실습 가능");
        particableCheckBox.setHorizontalAlignment(SwingConstants.LEFT);
        particableCheckBox.setFont(new Font("나눔고딕", Font.BOLD, 13));
        particableCheckBox.setBackground(new Color(255, 255, 255));
        checkBoxPanel.setLayout(new GridLayout(2, 2, 0, 2));
        checkBoxPanel.add(contentCheckBox);
        checkBoxPanel.add(beamProjectCheckBox);
        checkBoxPanel.add(reservationCheckBox);
        checkBoxPanel.add(recordingCheckBox);
        checkBoxPanel.add(particableCheckBox);

        //dropdown Panel 생성
        JPanel dropdownsPanel = new JPanel();
        dropdownsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        dropdownsPanel.setBackground(new Color(255, 255, 255));
        subMainPanel.add(dropdownsPanel);
        dropdownsPanel.setSize(new Dimension(800,200));
        dropdownsPanel.setLayout(new GridLayout(3, 2, 100, 10));

        // 공간 유형 라벨 생성
        JLabel usageLabel = new JLabel("\uACF5\uAC04 \uC720\uD615   : ");
        usageLabel.setHorizontalAlignment(SwingConstants.LEFT); // 수평 정렬 설정
        usageLabel.setFont(new Font("나눔고딕", Font.BOLD, 14)); // 폰트 설정
        dropdownsPanel.add(usageLabel); // 콤보박스 패널에 공간 유형 라벨 추가

        // 공간 유형 콤보박스 생성
        JComboBox usageComboBox = new JComboBox(new String[] { "선택", "교실", "교실 외" });
        usageComboBox.setBackground(new Color(255, 255, 255)); // 배경색 설정
        usageComboBox.setFont(new Font("나눔고딕", Font.BOLD, 14)); // 폰트 설정
        dropdownsPanel.add(usageComboBox); // 콤보박스 패널에 공간 유형 콤보박스 추가
        usageComboBox.setPreferredSize(new Dimension(200, usageComboBox.getPreferredSize().height)); // 크기 설정

        // 좌석 수 라벨 생성
        JLabel seatsLabel = new JLabel("\uC88C\uC11D \uC218       : ");
        seatsLabel.setHorizontalAlignment(SwingConstants.LEFT); // 수평 정렬 설정
        seatsLabel.setFont(new Font("나눔고딕", Font.BOLD, 14)); // 폰트 설정
        dropdownsPanel.add(seatsLabel); // 콤보박스 패널에 좌석 수 라벨 추가

        //좌석수를 JComboBox로 받아오기
        JComboBox seatsComboBox = new JComboBox(new String[]{"선택", "1-10", "11-20", "21-30", "31-40", "41-50", "51-60", "61-70", "71-80", "81-90"});
        seatsComboBox.setBackground(new Color(255, 255, 255));
        seatsComboBox.setFont(new Font("나눔고딕", Font.BOLD, 14));
        dropdownsPanel.add(seatsComboBox);

        // 카메라 유형 라벨 생성
        JLabel cameraLabel = new JLabel("\uCE74\uBA54\uB77C \uC720\uD615 :");
        dropdownsPanel.add(cameraLabel); // 콤보박스 패널에 카메라 유형 라벨 추가
        cameraLabel.setFont(new Font("나눔고딕", Font.BOLD, 14)); // 폰트 설정

        // 카메라 유형 콤보박스 생성
        JComboBox cameraComboBox = new JComboBox(new String[] { "선택", "고정식 카메라", "추적식 카메라" });
        cameraComboBox.setBackground(new Color(255, 255, 255)); // 배경색 설정
        cameraComboBox.setFont(new Font("나눔고딕", Font.BOLD, 14)); // 폰트 설정
        dropdownsPanel.add(cameraComboBox); // 콤보박스 패널에 카메라 유형 콤보박스 추가

        // 시간표 패널 생성
        JPanel timePanel = new JPanel(); // 시간표 패널 생성
        timePanel.setLayout(new GridLayout(8, 5)); // 그리드 레이아웃 설정
        timePanel.setBorder(BorderFactory.createTitledBorder("원하는 교시 선택")); // 테두리 설정
        timePanel.setBackground(new Color(255, 255, 255)); // 배경색 설정

        // 시간표 체크박스 생성 및 추가
        String[] days = {"월", "화", "수", "목", "금"};
        String[] times = { "1", "2", "3", "4", "5", "6", "7", "8" };

        for (String time : times) {
            for (String day : days) {
                String name = day + time;
                timeDictionary.put(name, false); // 초기값 설정
                JCheckBox checkBox = new JCheckBox(day + " " + time);
                checkBox.setBackground(new Color(255, 255, 255)); // 배경색 설정
                checkBox.addActionListener(new ActionListener() { // 체크박스 클릭 이벤트 리스너 추가
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // 선택된 부분에 대해서는 딕셔너리의 값을 true로 바꿔주기
                        timeDictionary.put(name, true); // 체크박스 상태 저장
                    }
                });
                checkBox.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // 테두리 설정
                timePanel.add(checkBox); // 시간표 패널에 체크박스 추가
                checkBoxList.add(checkBox);
            }
        }
        JScrollPane scrollPane = new JScrollPane(timePanel); // 스크롤 패널 생성
        mainPanel.add(scrollPane); // 메인 패널에 스크롤 패널 추가
        scrollPane.setBackground(new Color(255, 255, 255)); // 배경색 설정

        // 결과 버튼 및 홈 버튼 패널 생성
        JPanel ButtonPanel = new JPanel(); // 버튼 패널 생성
        ButtonPanel.setBackground(new Color(255, 255, 255)); // 배경색 설정
        contentPane.add(ButtonPanel, BorderLayout.SOUTH); // 콘텐츠 패널에 버튼 패널 추가
        ButtonPanel.setLayout(new BorderLayout(0, 0)); // 레이아웃 설정

        JPanel resultPanel = new JPanel(new FlowLayout()); // 결과 패널 생성
        resultPanel.setBackground(new Color(255, 255, 255)); // 배경색 설정
        ButtonPanel.add(resultPanel, BorderLayout.CENTER); // 버튼 패널에 결과 패널 추가
        JButton resultButton = new JButton("\uAC80\uC0C9"); // 검색 버튼 생성
        resultButton.setFont(new Font("나눔고딕", Font.BOLD, 16)); // 폰트 설정
        resultButton.setBackground(new Color(255, 255, 255)); // 배경색 설정
        resultPanel.add(resultButton); // 결과 패널에 검색 버튼 추가

        resultButton.addActionListener(new ActionListener() { // 검색 버튼 클릭 이벤트 리스너 추가
            @Override
            public void actionPerformed(ActionEvent e) {
                usage = (String) usageComboBox.getSelectedItem(); // 선택된 공간 유형 저장
                seats = (String) seatsComboBox.getSelectedItem(); // 선택된 좌석 수 저장
                cameraType = (String) cameraComboBox.getSelectedItem(); // 선택된 카메라 유형 저장
                content= contentCheckBox.isSelected();
                project = beamProjectCheckBox.isSelected(); // 빔프로젝트 체크박스 상태 저장
                reservation = reservationCheckBox.isSelected(); // 예약 필요 체크박스 상태 저장
                recording = recordingCheckBox.isSelected(); // 녹화 가능 체크박스 상태 저장
                practicable = particableCheckBox.isSelected(); // 실습 가능 체크박스 상태 저장
                infoArea = new JTextArea(); // 정보 표시 영역 생성
                infoArea.setFont(new Font("나눔고딕", Font.BOLD, 16));
                infoArea.setBorder(new EmptyBorder(10, 50, 20, 20));
                JScrollPane scroll = new JScrollPane(infoArea);
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        //스크롤바 위치 맨 위로 조정
                        scroll.getVerticalScrollBar().setValue(scroll.getVerticalScrollBar().getMinimum());
                    }
                });

                message = "검색된 교실의 번호: \n"; // message 변수 초기화


                //교실 정보를 선택한 경우
                if(usage.equals("교실")) searchClassroomInfo(seats, cameraType, content, project, reservation, recording, practicable);
                //교실 외의 정보를 선택한 경우
                if(usage.equals("교실 외")) searchClassroom_ExternalInfo(seats, content, project, reservation, recording, practicable);


                // 새로운 창을 열어 결과를 보여줌
                JFrame newFrame = new JFrame("검색된 정보"); // 새 프레임 생성
                newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 닫기 버튼 설정
                newFrame.setBackground(new Color(255, 255, 255)); // 배경색 설정
                newFrame.setBounds(100, 100, 1100, 600); // 새 프레임 크기 설정
                newFrame.setLocation(50, 50); // 새 프레임 위치 설정

                newFrame.getContentPane().setForeground(new Color(255, 255, 255)); // 전경색 설정
                newFrame.getContentPane().setLayout(new BorderLayout(10, 10)); // 레이아웃 설정

                infoArea.setEditable(false); // 정보 표시 영역 수정 불가 설정
                newFrame.getContentPane().add(scroll, BorderLayout.CENTER); // 새 프레임에 정보 표시 영역 추가
                newFrame.setVisible(true); // 새 프레임 보이기 설정

                // 로고 붙이는 Panel
                JPanel logoPanel = new JPanel(); // 로고 패널 생성
                logoPanel.setBackground(new Color(255, 255, 255)); // 배경색 설정
                logoPanel.setPreferredSize(new Dimension(1100, 103)); // 패널 선호 크기 설정
                newFrame.getContentPane().add(logoPanel, BorderLayout.NORTH); // 새 프레임에 로고 패널 추가
                logoPanel.setLayout(new GridLayout(2, 0, 0, 0)); // 그리드 레이아웃 설정
                newFrame.getContentPane().setBackground(new Color(255, 255, 255)); // 배경색 설정

                // GONG-GANG 로고 label 생성
                JLabel logo = new JLabel("Gong-Gang"); // 로고 라벨 생성
                logo.setBackground(new Color(255, 255, 255)); // 배경색 설정
                logo.setHorizontalAlignment(SwingConstants.CENTER); // 수평 정렬 설정
                logo.setFont(new Font("Arial Black", Font.BOLD, 40)); // 폰트 설정
                logoPanel.add(logo); // 로고 패널에 로고 라벨 추가

                JLabel userLabel = new JLabel("- 결과 -"); // 부제 라벨 생성
                userLabel.setHorizontalAlignment(SwingConstants.CENTER); // 수평 정렬 설정
                userLabel.setFont(new Font("나눔고딕", Font.BOLD, 22)); // 폰트 설정
                logoPanel.add(userLabel); // 로고 패널에 부제 라벨 추가

                newFrame.addWindowListener((WindowListener) new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        super.windowClosing(e);
                        // 새 창이 닫힐 때 timeTable 초기화
                        for (Map.Entry<String, Boolean> entry : timeDictionary.entrySet()) {
                            entry.setValue(false);
                        }
                        // 체크박스 선택 상태 초기화
                        for (JCheckBox checkBox : checkBoxList) {
                            checkBox.setSelected(false);
                        }
                    }
                });

            }

        });

        // 홈 버튼 생성
        JPanel homePanel = new JPanel(); // 홈 패널 생성
        homePanel.setBackground(new Color(255, 255, 255)); // 배경색 설정
        JButton homeButton = new JButton("HOME"); // 홈 버튼 생성
        ButtonPanel.add(homePanel, BorderLayout.EAST); // 버튼 패널에 홈 패널 추가
        homePanel.setLayout(new BorderLayout(0, 0)); // 레이아웃 설정
        homeButton.setBackground(new Color(255, 255, 255)); // 배경색 설정
        homeButton.setAlignmentX(Component.RIGHT_ALIGNMENT); // 정렬 설정
        homeButton.setFont(new Font("나눔고딕 ExtraBold", Font.BOLD, 12)); // 폰트 설정
        homePanel.add(homeButton, BorderLayout.SOUTH); // 홈 패널에 홈 버튼 추가

        // 홈 버튼에 ActionListener 추가
        homeButton.addActionListener(new ActionListener() { // 홈 버튼 클릭 이벤트 리스너 추가
            public void actionPerformed(ActionEvent e) {
                dispose(); // 현재 프레임 닫기
                mainGUI mainFrame = new mainGUI(); // mainGUI 객체 생성
                mainFrame.setVisible(true); // mainGUI 프레임 보이기
            }
        });

    }

    // 교실 정보 검색 메서드
    public void searchClassroomInfo(String seats, String cameraType, boolean content, boolean project, boolean reservation, boolean recording, boolean practicable) {
        String query = buildQuery(content, project, reservation, recording, practicable, 0, cameraType); // 쿼리를 생성하는 함수 호출 시 cameraType 전달
        executeQuery(seats, query, 0); // 해당 쿼리를 실행시키는 함수 호출
    }



    // 교실 외 정보를 찾는 함수
    private void searchClassroom_ExternalInfo(String seats, boolean content, boolean project, boolean reservation, boolean recording, boolean practicable) {
        StringBuilder warnMessage = new StringBuilder();

        if (recording) {
            warnMessage.append("녹화를 원한다면 교실을 선택해 주세요.\n");
        }
        if (project) {
            warnMessage.append("빔프로젝트를 원한다면 교실을 선택해 주세요.\n");
        }
        if (practicable) {
            warnMessage.append("실습을 원한다면 교실을 선택해 주세요.\n");
        }

        if (warnMessage.length() > 0) {
            infoArea.setText(warnMessage.toString().trim());
            return;
        }

        String query = buildQuery(content, false, false, false, false, 1, "선택"); // content 조건만 고려하여 쿼리 생성
        executeQuery(seats, query, 1); // 쿼리 실행
    }









    private String buildQuery(boolean content, boolean project, boolean reservation, boolean recording, boolean practicable, int type, String cameraType) {
        StringBuilder query = new StringBuilder("SELECT * FROM ");

        // type은 사용자가 찾기를 원하는 것이 교실(0)인지 교실 외(1)인지를 구분
        if (type == 0) {
            query.append("DB2024_ClassroomView");
            boolean hasCondition = false;

            if (practicable || project || reservation || recording || !cameraType.equals("선택")) {
                query.append(" WHERE");
                if (practicable) {
                    query.append(" Practicable='실습가능'");
                    hasCondition = true;
                }
                if (project) {
                    if (hasCondition) {
                        query.append(" AND");
                    }
                    query.append(" Projector='빔 있음'");
                    hasCondition = true;
                }
                if (reservation) {
                    if (hasCondition) {
                        query.append(" AND");
                    }
                    query.append(" Reservation_Required='예약 필요'");
                    hasCondition = true;
                }
                if (recording) {
                    if (hasCondition) {
                        query.append(" AND");
                    }
                    query.append(" Recording_Available='가능'");
                    hasCondition = true;
                }
                if (!cameraType.equals("선택")) {
                    if (hasCondition) {
                        query.append(" AND");
                    }
                    query.append(" CameraType='").append(cameraType).append("'");
                }
            }
        } else if (type == 1) {
            query.append("DB2024_ClassroomExternalView");
            if (content) {
                query.append(" WHERE Outlet_Count > 0");
            }
        }

        return query.toString();
    }



    // 쿼리 실행 메소드
    private void executeQuery(String seats, String query, int type) {
        final String url = "jdbc:mysql://localhost/DB2024Team05";
        final String user = "DB2024Team05";
        final String password = "DB2024Team05";

        message = "";  // message 초기화

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {  // 변경된 부분

            ResultSet rs = stmt.executeQuery();  // 쿼리 실행 및 결과 받기

            boolean found = false;
            while (rs.next()) {
                if (type == 0 && processResultSet(seats, rs, conn)) {
                    found = true;
                } else if (type == 1 && isNumberInRange(seats, rs.getInt("Seat_Count"))) {
                    message += formatRoomInfo(rs);
                    found = true;
                }
            }

            infoArea.setText(found ? message : "원하는 교실이 없습니다. 조건을 재선택하세요.");  // 결과값이 없는 경우

        } catch (SQLException e) {
            infoArea.setText("데이터를 불러오는 과정에서 오류가 있습니다 \n" + e.getMessage());  // DB 연결이 실패한 경우
        }
    }


    private boolean processResultSet(String seats, ResultSet rs, Connection conn) throws SQLException {
        boolean hasFalse = false; // 각 시간대에 대해 결과가 false인지 확인하는 변수

        // 각 시간대에 대해 반복
        for (Map.Entry<String, Boolean> entry : timeDictionary.entrySet()) {
            String key = entry.getKey();
            Boolean value = entry.getValue();

            // 만약 현재 시간대에 대한 값이 true이고 좌석 수가 조건에 맞는다면
            if (value && isNumberInRange(seats, rs.getInt("Seat_Count"))) {
                String query2 = "SELECT * FROM DB2024_Classroom_Schedule WHERE Room_Number=? AND Lecture_Time=?";

                PreparedStatement stmt = conn.prepareStatement(query2);
                stmt.setString(1, rs.getString("Room_Number"));
                stmt.setString(2, key);


                ResultSet rs2 = stmt.executeQuery();

                // 현재 시간대에 대한 결과가 없는 경우
                if (!rs2.next()) {
                    // 결과가 없으면 hasFalse를 true로 설정
                    message+=rs.getString("Room_Number");
                    message+=" "+key+"가능\n";
                    hasFalse=true;
                } else {
                    // 결과가 있으면 바로 false 반환
                    //앞에서 한번만 true로 바꿔주면 있기는 한거니까 hsaFalse를 다시 false로 바꿀 필요는 없음.

                }
            }
        }
        // 각 시간대에 대해 결과가 없는 경우에만 true 반환
        return hasFalse;
    }
    //검색된 결과에서 Room_number를 가져오고 출력할 수 있도록 만들어주는 코드
    private String formatRoomInfo(ResultSet rs) throws SQLException {
        String result=rs.getString("Room_number") +" "+rs.getString("Room_Name")+ " 가능\n";

        return result;
    }

    // 좌석 수를 사용자에게 범위로 받았기에 이를 테이블의 값과 비교하기 위한 코드
    public static boolean isNumberInRange(String range, int number) {
        String[] parts = range.split("-");

        int start = Integer.parseInt(parts[0].trim());
        int end = Integer.parseInt(parts[1].trim());

        return number >= start && number <= end;

    }


}