
package student;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import mainFrame.mainGUI;

/*
DB2024_LectureView 뷰를 사용
결과 창에 강의실 번호, 이름, 위치 출력하도록 수정
*/


public class student_button2 extends JFrame{

    private JPanel contentPane;
    private JTextField lectureNumberField;
    private JButton searchButton;
    private JTextArea infoArea;

    public student_button2() {

        // main frame 설정
        setTitle("GONG-GANG");
        setBackground(new Color(255, 255, 255));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1100, 600);
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
        // - 수업 강의실 찾기 - 로고 label 생성
        JLabel userLabel = new JLabel("- \uC218\uC5C5 \uAC15\uC758\uC2E4 \uCC3E\uAE30 -");
        userLabel.setHorizontalAlignment(SwingConstants.CENTER);
        userLabel.setFont(new Font("나눔고딕", Font.BOLD, 22));
        logoPanel.add(userLabel);


        // inputPanel & resultPanel 포함하는 mainPanel
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(255, 255, 255));
        contentPane.add(mainPanel, BorderLayout.CENTER);
        mainPanel.setLayout(new GridLayout(0, 1, 0, 0));

        // 입력 받는 inputPanel
        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(new Color(255,255,255));
        inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));
        mainPanel.add(inputPanel);

        // 학수번호나 강의이름과 분반 번호를 입력하세요' Label 생성
        JLabel inputLabel = new JLabel("학수번호나 강의이름과 분반 번호를 입력하세요 (ex 20471-3, 데이터베이스-3)");
        inputLabel.setVerticalAlignment(SwingConstants.BOTTOM);
        inputLabel.setPreferredSize(new Dimension(1050, 80));
        inputLabel.setHorizontalAlignment(SwingConstants.CENTER);
        inputLabel.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 20));
        inputLabel.setBounds(200, 400, WIDTH, HEIGHT);
        inputPanel.add(inputLabel);

        // 결과 보여주는 TextField 생성
        lectureNumberField = new JTextField(17);
        inputPanel.add(lectureNumberField);

        // 결과 출력을 위한 searchButton 생성
        searchButton = new JButton("Search");
        searchButton.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 12));
        searchButton.setBackground(new Color(255, 255, 255));
        inputPanel.add(searchButton);


        //입력후 search 버튼을 눌렀을 경우
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String lectureNumber = lectureNumberField.getText();//텍스트를 받아온 뒤에
                if (!lectureNumber.isEmpty()) {
                    showRoomNumber(lectureNumber);//해당 강의의 공간번호를 보여주는 함수 호출
                } else {
                    JOptionPane.showMessageDialog(null, "학수 번호를 입력하세요.");
                }
            }
        });

        inputPanel.add(inputLabel);
        inputPanel.add(lectureNumberField);
        inputPanel.add(searchButton);

        //JText Area로 결과 표시
        infoArea = new JTextArea(5,20);
        infoArea.setEditable(false);
        JScrollPane ScrollPane = new JScrollPane(infoArea);
        mainPanel.add(ScrollPane);


        //home button
        JPanel homeButtonPanel = new JPanel();
        homeButtonPanel.setBackground(new Color(255, 255, 255));
        contentPane.add(homeButtonPanel, BorderLayout.SOUTH);

        JButton homeButton = new JButton("HOME");
        homeButton.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 12));
        homeButton.setBackground(new Color(255, 255, 255));
        homeButtonPanel.add(homeButton);
        homeButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JPanel westPanel = new JPanel();
        westPanel.setBackground(new Color(255, 255, 255));
        contentPane.add(westPanel, BorderLayout.WEST);

        JPanel eastPanel = new JPanel();
        eastPanel.setBackground(new Color(255, 255, 255));
        contentPane.add(eastPanel, BorderLayout.EAST);

        // Add ActionListener to homeButton
        homeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close current frame
                mainGUI mainFrame = new mainGUI();
                mainFrame.setVisible(true); // Open the mainGUI frame
            }
        });
    }

    //강의번호나 강의이름에 해당하는 공간번호를 보여주는 코드
    private void showRoomNumber(String lectureNumber) {
        String dbUrl = "jdbc:mysql://localhost/DB2024Team05";
        String dbUser = "DB2024Team05";
        String dbPass = "DB2024Team05";

        // 학수번호와 함께 받아와서 분리
        String[] parts = lectureNumber.split("-");
        if (parts.length != 2) {
            JOptionPane.showMessageDialog(this, "입력값을 확인하세요");
            return; // 입력이 올바르게 분리되지 않은 경우
        }

        // 서브 쿼리를 사용하여 해당 강의실의 추가적인 특성 정보를 조회하는 쿼리
        String query = "SELECT * FROM DB2024_ClassroomView " +
                "WHERE Room_Number = (SELECT Room_Number FROM DB2024_LectureView ";
        if (isInteger(parts[0])) {
            query += "WHERE Lecture_Num = ? AND Class_Num = ?)";
        } else {
            query += "WHERE Lecture_Name = ? AND Class_Num = ?)";
        }

        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, parts[0]); // Lecture_Num or Lecture_Name
            stmt.setString(2, parts[1]); // Class_Num

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String roomNumber = rs.getString("Room_Number");
                    String roomName = rs.getString("Room_Name");
                    String location = rs.getString("Location");
                    int seatCount = rs.getInt("Seat_Count");
                    String projector = rs.getString("Projector");
                    String reservationRequired = rs.getString("Reservation_Required");
                    int outletCount = rs.getInt("Outlet_Count");

                    if (isInteger(parts[0])) {
                        infoArea.setText("강의실 번호: " + roomNumber +  "\n강의실 이름: " + roomName +
                                "\n강의실 위치: " + location +
                                "\n좌석 수: " + seatCount +
                                "\n프로젝터: " + projector +
                                "\n예약 필요: " + reservationRequired +
                                "\n콘센트 개수: " + outletCount);
                    } else {
                        infoArea.setText("강의실 번호: " + roomNumber +
                                "\n강의실 이름: " + roomName +
                                "\n강의실 위치: " + location +
                                "\n좌석 수: " + seatCount +
                                "\n프로젝터: " + projector +
                                "\n예약 필요: " + reservationRequired +
                                "\n콘센트 개수: " + outletCount);
                    }

                } else {
                    JOptionPane.showMessageDialog(this, "해당 강의실의 추가 정보를 찾을 수 없습니다.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }




    //학수번호를 입력한 것인지 확인하기 위한 문자열의 숫자 구성 판단 함수
    public boolean isInteger(String strValue){
        try {
            Integer.parseInt(strValue);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }


}