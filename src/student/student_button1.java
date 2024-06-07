package student;

//원하는 공간을 찾기 위한 코드

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import mainFrame.mainGUI;

import java.awt.FlowLayout;
import java.awt.Component;

public class student_button1 extends JFrame{


    private JPanel contentPane;

    String usage;            // 사용 유형
    String seats;            // 좌석 수
    boolean content;         // 콘센트 유무
    boolean project;         // 빔프로젝트 유무
    boolean eat;             // 식사 가능 유무
    boolean computer;        // 컴퓨터 유무
    Map<String, Boolean> timeDictionary=new HashMap<>();
    ArrayList<JCheckBox> checkBoxList = new ArrayList<>();
    private JTextArea infoArea; // 결과 가져오는 텍스트
    String message = null;

    public student_button1() {



        // main frame 설정
        setTitle("GONG-GANG");
        setBackground(new Color(255, 255, 255));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1100, 600); // frame size 1100X600
        setLocation(50, 50);

        // main JPanel 생성
        contentPane = new JPanel();
        contentPane.setForeground(new Color(255, 255, 255));
        contentPane.setBackground(new Color(255, 255, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(10, 10));
        setContentPane(contentPane);


        // 로고 붙이는 Panel
        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(new Color(255, 255, 255));
        logoPanel.setPreferredSize(new Dimension(1100, 103)); // Set preferred size for the North panel
        contentPane.add(logoPanel, BorderLayout.NORTH);
        logoPanel.setLayout(new GridLayout(2, 0, 0, 0));

        // GONG-GANG 로고 label 생성
        JLabel logo = new JLabel("Gong-Gang");
        logo.setBackground(new Color(255, 255, 255));
        logo.setHorizontalAlignment(SwingConstants.CENTER);
        logo.setFont(new Font("Arial Black", Font.BOLD, 40));
        logoPanel.add(logo);

        // - 원하는 강의실 찾기 - 로고 label 생성
        JLabel userLabel = new JLabel("- \uC6D0\uD558\uB294 \uAC15\uC758\uC2E4 \uCC3E\uAE30 -");
        userLabel.setHorizontalAlignment(SwingConstants.CENTER);
        userLabel.setFont(new Font("나눔고딕", Font.BOLD, 22));
        logoPanel.add(userLabel);

        // option & time table 붙이는 mainPanel 생성
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(255, 255, 255));
        contentPane.add(mainPanel, BorderLayout.CENTER);
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
        JCheckBox eatCheckBox = new JCheckBox("식사");
        eatCheckBox.setHorizontalAlignment(SwingConstants.LEFT);
        eatCheckBox.setFont(new Font("나눔고딕", Font.BOLD, 13));
        eatCheckBox.setBackground(new Color(255, 255, 255));
        JCheckBox computerCheckBox=new JCheckBox("컴퓨터");
        computerCheckBox.setFont(new Font("나눔고딕", Font.BOLD, 13));
        computerCheckBox.setBackground(new Color(255, 255, 255));
        checkBoxPanel.setLayout(new GridLayout(2, 2, 0, 2));
        checkBoxPanel.add(contentCheckBox);
        checkBoxPanel.add(beamProjectCheckBox);
        checkBoxPanel.add(eatCheckBox);
        checkBoxPanel.add(computerCheckBox);

        //dropdown Panel 생성
        JPanel dropdownsPanel = new JPanel();
        dropdownsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        dropdownsPanel.setBackground(new Color(255, 255, 255));
        subMainPanel.add(dropdownsPanel);
        dropdownsPanel.setSize(new Dimension(800,200));
        dropdownsPanel.setLayout(new GridLayout(2, 2, 100, 10));

        // ComboBox1 - 공간유형
        JLabel usageLabel = new JLabel("\uACF5\uAC04 \uC720\uD615 : ");
        usageLabel.setHorizontalAlignment(SwingConstants.LEFT);
        usageLabel.setFont(new Font("나눔고딕", Font.BOLD, 14));
        dropdownsPanel.add(usageLabel);

        //찾고자하는 공간의 유형 선택
        JComboBox usageComboBox = new JComboBox(new String[]{"선택", "교실", "교실 외"});
        usageComboBox.setBackground(new Color(255, 255, 255));
        usageComboBox.setFont(new Font("나눔고딕", Font.BOLD, 14));
        dropdownsPanel.add(usageComboBox);
        usageComboBox.setPreferredSize(new Dimension(200, usageComboBox.getPreferredSize().height));

        // ComboBox2 - 좌석 수
        JLabel seatsLabel = new JLabel("\uC88C\uC11D \uC218    : ");
        seatsLabel.setHorizontalAlignment(SwingConstants.LEFT);
        seatsLabel.setFont(new Font("나눔고딕", Font.BOLD, 14));
        dropdownsPanel.add(seatsLabel);

        //좌석수를 JComboBox로 받아오기
        JComboBox seatsComboBox = new JComboBox(new String[]{"선택", "1-10", "11-20", "21-30", "31-40", "41-50", "51-60", "61-70", "71-80", "81-90"});
        seatsComboBox.setBackground(new Color(255, 255, 255));
        seatsComboBox.setFont(new Font("나눔고딕", Font.BOLD, 14));
        dropdownsPanel.add(seatsComboBox);


        // 검색 원하는 시간 받아오기
        // 시간표 패널 생성
        JPanel timePanel = new JPanel();
        timePanel.setLayout(new GridLayout(8, 5));
        timePanel.setBorder(BorderFactory.createTitledBorder("원하는 교시 선택"));
        timePanel.setBackground(new Color(255,255,255));

        // 시간표 라벨 및 체크박스 생성
        String[] days = {"월", "화", "수", "목", "금"};
        String[] times = {"1", "2", "3", "4", "5", "6", "7","8"};

        for (String time : times) {
            for (String day : days) {
                String name=day+time;
                timeDictionary.put(name,false);
                JCheckBox checkBox = new JCheckBox(day + " " + time);
                checkBox.setBackground(new Color(255,255,255));
                checkBox.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // 선택된 부분에 대해서는 딕셔너리의 값을 true로 바꿔주기
                        timeDictionary.put(name, true);

                    }
                });
                checkBox.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                timePanel.add(checkBox);
                checkBoxList.add(checkBox);

            }
        }
        JScrollPane scrollPane = new JScrollPane(timePanel);
        mainPanel.add(scrollPane);
        scrollPane.setBackground(new Color(255,255,255));


        // resultButton & homeButton 붙이는 Panel 생성
        JPanel ButtonPanel = new JPanel();
        ButtonPanel.setBackground(new Color(255, 255, 255));
        contentPane.add(ButtonPanel, BorderLayout.SOUTH);
        ButtonPanel.setLayout(new BorderLayout(0, 0));

        // reuslt 버튼 붙이는 Panel 생성
        JPanel resultPanel = new JPanel(new FlowLayout());
        resultPanel.setBackground(new Color(255, 255, 255));
        ButtonPanel.add(resultPanel, BorderLayout.CENTER);
        // result 버튼 생성
        JButton resultButton = new JButton("\uAC80\uC0C9");
        resultButton.setFont(new Font("나눔고딕", Font.BOLD, 16));
        resultButton.setBackground(new Color(255, 255, 255));
        resultPanel.add(resultButton);

        // 디자인을 위한 빈 패널 생성
        JPanel emptyPanel = new JPanel();
        emptyPanel.setBackground(new Color(255,255,255));
        ButtonPanel.add(emptyPanel, BorderLayout.WEST);
        emptyPanel.setPreferredSize(new Dimension(90,0));

        resultButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usage=(String)usageComboBox.getSelectedItem();
                seats=(String)seatsComboBox.getSelectedItem();

                content= contentCheckBox.isSelected();
                project= beamProjectCheckBox.isSelected();
                eat= eatCheckBox.isSelected();
                computer= computerCheckBox.isSelected();
                infoArea=new JTextArea();
                infoArea.setFont(new Font("나눔고딕", Font.BOLD, 16));
                infoArea.setBorder(new EmptyBorder(10, 50, 20, 20));
                JScrollPane scroll = new JScrollPane(infoArea);
                scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        //스크롤바 위치 맨 위로 조정
                        scroll.getVerticalScrollBar().setValue(scroll.getVerticalScrollBar().getMinimum());
                    }
                });

                message = "검색된 교실의 번호: \n";
                //교실 정보를 선택한 경우
                if(usage.equals("교실")) searchClassroomInfo(seats, content, project, eat, computer);
                //교실 외의 정보를 선택한 경우
                if(usage.equals("교실 외")) searchClassroom_ExternalInfo(seats, content, project, eat, computer);

                // 새 창을 여는 로직
                JFrame newFrame = new JFrame("검색된 정보");
                newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                newFrame.setBackground(new Color(255, 255, 255));
                newFrame.setBounds(100, 100, 1100, 600);
                newFrame.setLocation(50, 50);

                newFrame.getContentPane().setForeground(new Color(255, 255, 255));
                newFrame.getContentPane().setLayout(new BorderLayout(10,10));


                infoArea.setEditable(false);
                newFrame.getContentPane().add(scroll, BorderLayout.CENTER);
                newFrame.setVisible(true);

                // 로고 붙이는 Panel
                JPanel logoPanel = new JPanel();
                logoPanel.setBackground(new Color(255, 255, 255));
                logoPanel.setPreferredSize(new Dimension(1100, 103)); // Set preferred size for the North panel
                newFrame.getContentPane().add(logoPanel, BorderLayout.NORTH);
                logoPanel.setLayout(new GridLayout(2, 0, 0, 0));
                newFrame.getContentPane().setBackground(new Color(255,255,255));

                // GONG-GANG 로고 label 생성
                JLabel logo = new JLabel("Gong-Gang");
                logo.setBackground(new Color(255, 255, 255));
                logo.setHorizontalAlignment(SwingConstants.CENTER);
                logo.setFont(new Font("Arial Black", Font.BOLD, 40));
                logoPanel.add(logo);

                //- 결과 - 로고 label 생성
                JLabel userLabel = new JLabel("- 결과 -");
                userLabel.setHorizontalAlignment(SwingConstants.CENTER);
                userLabel.setFont(new Font("나눔고딕", Font.BOLD, 22));
                logoPanel.add(userLabel);

                newFrame.addWindowListener(new WindowAdapter() {
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

        //homebuttom
        JPanel homePanel = new JPanel();
        homePanel.setBackground(new Color(255, 255, 255));
        JButton homeButton = new JButton("HOME");
        ButtonPanel.add(homePanel, BorderLayout.EAST);
        homePanel.setLayout(new BorderLayout(0, 0));
        homeButton.setBackground(new Color(255, 255, 255));
        homeButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
        homeButton.setFont(new Font("나눔고딕 ExtraBold", Font.BOLD, 12));
        homePanel.add(homeButton, BorderLayout.SOUTH);



        // Add ActionListener to homeButton
        homeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close current frame
                mainGUI mainFrame = new mainGUI();
                mainFrame.setVisible(true); // Open the mainGUI frame
            }
        });


    }

    //교실 정보를 찾는 함수
    private void searchClassroomInfo(String seats, boolean content, boolean project, boolean eat, boolean computer) {
        if (eat) {
            //교실에서 식사를 원할 경우 아래와 같은 문자를 화면에 보이게 한다.
            infoArea.setText("교실에서는 취식이 불가능합니다. 재선택 해주세요.");
            return;
        }

        String query = buildQuery(content, project, eat, computer, 0);// 쿼리를 생성하는 함수를 실행하고
        executeQuery(seats, query, 0);//해당 쿼리를 실행시키는 함수를 호출한다. 여기서 0은 교실외가 아니라 교실을 선택했음을 알려주기 위한 숫자.
    }

    //교실 외 정보를 찾는 함수
    private void searchClassroom_ExternalInfo(String seats, boolean content, boolean project, boolean eat, boolean computer) {
        String warnMessage="";
        if (computer) {
            //컴퓨터를 사용하길 원하는 경우
            warnMessage+="컴퓨터 실습을 원한다면 교실을 선택해 주세요.";
            if(project)
                //빔 프로젝트도 사용하길 원하는 경우
                warnMessage+="\n빔프로젝트를 원한다면 교실을 선택해주세요.";
            infoArea.setText(warnMessage);
            return;
        } else if (project) {
            //빔 프로젝트를 원하는 경우
            warnMessage+="빔프로젝트 원한다면 교실을 선택해 주세요.";
            infoArea.setText(warnMessage);
            return;
        }
        String query = buildQuery(content, project, eat, computer, 1); //빔프로젝트나 컴퓨터 실습을 원하는 상황이 아닌경우 쿼리를 만드는 함수를 호출
        executeQuery(seats, query, 1);//쿼리 실행
    }


    //쿼리를 생성하는 코드
    private String buildQuery(boolean content, boolean project, boolean eat, boolean computer, int type) {

        String query = "SELECT * FROM ";
        //type은 사용자가 찾기를 원하는 것이 교실(0)인지 교실 외(1)인지를 구분
        if(type==0){
            query+="DB2024_ClassroomView";
            if (computer || project) {
                //교실에서 컴퓨터와 빔프로젝트를 원하는 경우에는 조건을 붙여준다.  교실에서는 식사가 불가하고 모든 교실에 콘센트가 있으므로 해당하는 조건은 추가하지 않아도 됨
                query += " WHERE";
                if (computer) {
                    query += " Practicable='실습가능'";
                    if (project) {
                        query += " AND";
                    }
                }
                if (project) {
                    query += " Projector='빔 있음'";
                }
            }


        }if (type==1) {
            query += "DB2024_ClassroomExternalView";
            //교실 외에서는 콘센트 필요유무, 식사 가능 여부에 대해서 검색 조건을 걸어줄 수 있음.
            if(content) {
                query+=" WHERE Outlet_Count > 0 ";
                if(eat) query+="AND Eat_Available = 1";
                query+=";";
            }
            else{
                if(eat) query+=" WHERE Eat_Available = 1;";
            }

        }

        return query;
    }


    //쿼리를 input으로 받아서 해당 쿼리를 실행하는 메소드 이후에 해당하는 쿼리를 실행한 결과를 받아 infoArea에 붙이기
    private void executeQuery(String seats, String query, int type) {
        final String url = "jdbc:mysql://localhost/DB2024Team05";
        final String user = "DB2024Team05";
        final String password = "DB2024Team05";


        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();

        ) {
            ResultSet rs = stmt.executeQuery(query);//연결 맺고 실행한 결과를 받아오기

            boolean found = false;
            while (rs.next()) {
                if (type==0 && processResultSet(seats, rs, conn)) {
                    found = true;
                }else if((type == 1) && isNumberInRange(seats, rs.getInt("Seat_Count"))){
                    message += formatRoomInfo(rs);
                    found = true;
                }
            }

            infoArea.setText(found ? message : "원하는 교실이 없습니다. 조건을 재선택하세요.");//결과값이 없는 경우

        } catch (SQLException e) {
            infoArea.setText("데이터를 불러오는 과정에서 오류가 있습니다 \n" + e.getMessage());//DB 연결이 실패한 경우
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