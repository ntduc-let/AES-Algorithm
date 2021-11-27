package aes;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author ntduc-let
 */
public class AESForm extends javax.swing.JFrame {

    public static final int TYPE_HEX = 0;
    public static final int TYPE_UTF8 = 1;
    public static final int LENGTH_TIME = 17;
    public static final String URL_ICON = ".\\icon\\AES_icon.png";
    public static final String KEY_SIZE_128 = "128";
    public static final String KEY_SIZE_192 = "192";
    public static final String KEY_SIZE_256 = "256";
    public static final String LOOP_NUMBER_10 = "10";
    public static final String LOOP_NUMBER_12 = "12";
    public static final String LOOP_NUMBER_14 = "14";
    
    private final DocumentListener dlKey1 = new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
            updateNotiKey(cbbTypeKey1, txtKey1, txtNotiKey1);
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            updateNotiKey(cbbTypeKey1, txtKey1, txtNotiKey1);
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            updateNotiKey(cbbTypeKey1, txtKey1, txtNotiKey1);
        }
    };
    private final DocumentListener dlKey2 = new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
            updateNotiKey(cbbTypeKey2, txtKey2, txtNotiKey2);
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            updateNotiKey(cbbTypeKey2, txtKey2, txtNotiKey2);
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            updateNotiKey(cbbTypeKey2, txtKey2, txtNotiKey2);
        }
    };
    private final ActionListener myClick = new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource().equals(cbbSizeKey)){
                cbbSizeKeyClick();
            }else if(e.getSource().equals(btnExit)){
                btnExitClick();
            }else if(e.getSource().equals(btnReset1)){
                btnReset1Click();
            }else if(e.getSource().equals(btnEncode)){
                btnEncodeClick();
            }else if(e.getSource().equals(btnCopy1)){
                btnCopyClick(txtCipher1.getText());
            }else if(e.getSource().equals(btnReset2)){
                btnReset2Click();
            }else if(e.getSource().equals(btnDecode)){
                btnDecodeClick();
            }else if(e.getSource().equals(btnCopy2)){
                btnCopyClick(txtPlain2.getText());
            }
        }
    };
    /**
     * Creates new form AESForm
     */
    public AESForm() {
        initComponents();

        //Đặt icon
        Image icon = Toolkit.getDefaultToolkit().getImage(URL_ICON);
        setIconImage(icon);

        //Đặt form ra giữa
        setLocationRelativeTo(null);

        //gán sự kiện
        txtKey1.getDocument().addDocumentListener(dlKey1);
        txtKey2.getDocument().addDocumentListener(dlKey2);
        cbbSizeKey.addActionListener(myClick);
        btnExit.addActionListener(myClick);
        btnReset1.addActionListener(myClick);
        btnEncode.addActionListener(myClick);
        btnCopy1.addActionListener(myClick);
        btnReset2.addActionListener(myClick);
        btnDecode.addActionListener(myClick);
        btnCopy2.addActionListener(myClick);
        
        //Tắt chức năng UTF-8 của bản mã
        cbbTypeCipher1.setEnabled(false);
        cbbTypeCipher2.setEnabled(false);
    }

    //Cập nhật cảnh báo khóa
    private void updateNotiKey(JComboBox cbbTypeKey, JTextField txtKey, JLabel txtNotiKey) {
        int sizeKey = Integer.parseInt(cbbSizeKey.getSelectedItem().toString());
        int lengKey = sizeKey/8;

        switch (cbbTypeKey.getSelectedIndex()) {
            case TYPE_HEX -> {
                if (txtKey.getText().length() == 0 || txtKey.getText().length() == lengKey * 2) {
                    txtNotiKey.setText(" ");
                } else if (txtKey.getText().length() < lengKey * 2) {
                    txtNotiKey.setText("Độ dài khóa chưa đủ");
                } else if (txtKey.getText().length() > lengKey * 2) {
                    txtNotiKey.setText("Độ dài khóa vượt quá");
                }
            }
            case TYPE_UTF8 -> {
                int length = txtKey.getText().getBytes(StandardCharsets.UTF_8).length;
                if (length == 0 || length == lengKey) {
                    txtNotiKey.setText(" ");
                } else if (length < lengKey) {
                    txtNotiKey.setText("Độ dài khóa chưa đủ");
                } else if (length > lengKey) {
                    txtNotiKey.setText("Độ dài khóa vượt quá");
                }
            }
        }
    }
    
    //Thay đổi kích thước khóa
    private void cbbSizeKeyClick(){
        //Cập nhật cảnh báo khóa
        updateNotiKey(cbbTypeKey1, txtKey1, txtNotiKey1);
        updateNotiKey(cbbTypeKey2, txtKey2, txtNotiKey2);
        
        //Hiển thị số vòng lặp
        String sizeKey = cbbSizeKey.getSelectedItem().toString();
        switch (sizeKey) {
            case KEY_SIZE_128 -> txtLoopNumber.setText(LOOP_NUMBER_10);
            case KEY_SIZE_192 -> txtLoopNumber.setText(LOOP_NUMBER_12);
            case KEY_SIZE_256 -> txtLoopNumber.setText(LOOP_NUMBER_14);
        }
    }
    
    //Thoát chương trình
    private void btnExitClick(){
        System.exit(0); 
    }
    
    //Reset form Mã hóa
    private void btnReset1Click(){
        txtCipher1.setText("");
        txtPlain1.setText("");
        txtKey1.setText("");
        txtTime1.setText("00:00:00.00");
        txtNotiKey1.setText(" ");

        cbbTypeCipher1.setSelectedIndex(0);
        cbbTypePlain1.setSelectedIndex(0);
        cbbTypeKey1.setSelectedIndex(0);
    }
    
    //Thực hiện mã hóa
    private void btnEncodeClick(){
        //Cập nhật cảnh báo        
        txtNotiPlain1.setText(" ");        
        if(txtPlain1.getText().isEmpty()){
            txtNotiPlain1.setText("Vui lòng nhập bản rõ!");
            return;
        }else if(txtKey1.getText().isEmpty()){
            txtNotiKey1.setText("Vui lòng nhập khóa");
            return;
        }
        
        LocalTime timeBegin, timeEnd, timeDelay; //Tính thời gian thực thi
        timeBegin = LocalTime.now(); //Lấy thời gian bắt đầu thực thi

        int size = 0; //Kích thước khóa
        int ver = 0; //Số phiên bản

        String strCipher = ""; //Chuỗi đã mã hóa

        byte[] byteKey = null; //Mảng khóa
        int[] byteKeyExpansion; //Mảng khóa mở rộng

        byte[] bytePlain = null; //Mảng byte chuyển từ chuỗi bản rõ đầu vào
        byte[][][] bytePlainConvert; //Mảng byte chuyển từ mảng bytePlain

        size = Integer.parseInt(cbbSizeKey.getSelectedItem().toString()); //Lấy kích thước khóa

        AESAlgorithm aes = new AESAlgorithm(size);

        //Bắt lỗi không đúng định dạng
        try {
            bytePlain = getByte(aes, cbbTypePlain1, txtPlain1.getText()); //Chuyển chuỗi bản rõ thành mảng byte[]
        } catch (Exception e) {
            txtNotiPlain1.setText(e.getMessage());
            return;
        }
        
        ver = getVer(bytePlain); //Xác định số phiên bản
                
        //Bắt lỗi không đúng định dạng
        try {
            byteKey = getByte(aes, cbbTypeKey1, txtKey1.getText()); //Chuyển chuỗi khóa thành mảng byte[]
        } catch (Exception e) {
            txtNotiKey1.setText(e.getMessage());
            return;
        }
        
        //Kiểm tra độ dài khóa
        if(byteKey.length != size/8){
            return;
        }
        
        byteKeyExpansion = aes.createKeyExpansion(byteKey); //Tạo khóa mở rộng

        bytePlainConvert = getByteConvert(ver, bytePlain); //Chuyển mã thành nhiều phiên bản byte[][]
        
        strCipher = getStrEncode(aes, ver, byteKeyExpansion, cbbTypeCipher1, bytePlainConvert); //Chuỗi đã mã hóa

        txtCipher1.setText(strCipher);

        timeEnd = LocalTime.now(); //lấy thời gian kết thúc thực thi

        //Tính khoảng thời gian thực thi
        timeDelay = timeEnd.minusHours(timeBegin.getHour())
                .minusMinutes(timeBegin.getMinute())
                .minusSeconds(timeBegin.getSecond())
                .minusNanos(timeBegin.getNano());
        
        String strTimeDelay = "" + timeDelay; //00:00:00.00000000

        if (strTimeDelay.length() <= LENGTH_TIME) {
            txtTime1.setText(strTimeDelay);
        } else {
            txtTime1.setText(strTimeDelay.substring(0, LENGTH_TIME - 1));
        }
    }
    
    //Reset form Giải mã
    private void btnReset2Click(){
        txtCipher2.setText("");
        txtPlain2.setText("");
        txtKey2.setText("");
        txtTime2.setText("00:00:00.00");
        txtNotiKey2.setText(" ");

        cbbTypeCipher2.setSelectedIndex(0);
        cbbTypePlain2.setSelectedIndex(0);
        cbbTypeKey2.setSelectedIndex(0);
    }
    
    //Thực hiện giải mã
    private void btnDecodeClick(){
        //Cập nhật cảnh báo
        txtNotiCipher2.setText(" ");
        if(txtCipher2.getText().isEmpty()){
            txtNotiCipher2.setText("Vui lòng nhập bản mã!");
            return;
        }else if(txtKey2.getText().isEmpty()){
            txtNotiKey2.setText("Vui lòng nhập khóa");
            return;
        }
        
        LocalTime timeBegin, timeEnd, timeDelay; //Tính thời gian thực thi
        timeBegin = LocalTime.now(); //Lấy thời gian bắt đầu thực thi

        int size = 0; //Kích thước khóa
        int ver = 0; //Số phiên bản

        String strPlain = ""; //Chuỗi đã giải mã

        byte[] byteKey = null; //Mảng khóa
        int[] byteKeyExpansion; //Mảng khóa mở rộng

        byte[] byteCipher = null; //Mảng byte chuyển từ chuỗi bản mã đầu vào
        byte[][][] byteCipherConvert; //Mảng byte chuyển từ mảng byteCipher

        size = Integer.parseInt(cbbSizeKey.getSelectedItem().toString()); //Lấy kích thước khóa

        AESAlgorithm aes = new AESAlgorithm(size);

        //Bắt lỗi không đúng định dạng
        try {
            byteCipher = getByte(aes, cbbTypeCipher2, txtCipher2.getText()); //Chuyển chuỗi bản mã thành mảng byte[]
        } catch (Exception e) {
            txtNotiCipher2.setText(e.getMessage());
            return;
        }
        
        //Kiểm tra bản mã đã đủ kí tự chưa
        if(byteCipher.length%16!=0){
            txtNotiCipher2.setText("Bản mã không hợp lệ");
            return;
        }
        
        ver = getVer(byteCipher); //Xác định số phiên bản

        try {
            byteKey = getByte(aes, cbbTypeKey2, txtKey2.getText()); //Chuyển chuỗi key thành mảng byte[]
        } catch (Exception e) {
            txtNotiKey2.setText(e.getMessage());
            return;
        }
        
        if(byteKey.length != size/8){
            return;
        }
        
        byteKeyExpansion = aes.createKeyExpansion(byteKey); //Tạo khóa mở rộng
        
        byteCipherConvert = getByteConvert(ver, byteCipher); //Chuyển mã thành nhiều phiên bản byte[][]

        strPlain = getStrDecode(aes, ver, byteKeyExpansion, cbbTypePlain2, byteCipherConvert); //Chuỗi đã giải mã

        txtPlain2.setText(strPlain);

        timeEnd = LocalTime.now(); //lấy thời gian kết thúc thực thi

        //Tính khoảng thời gian thực thi
        timeDelay = timeEnd.minusHours(timeBegin.getHour())
                .minusMinutes(timeBegin.getMinute())
                .minusSeconds(timeBegin.getSecond())
                .minusNanos(timeBegin.getNano());

        String strTimeDelay = "" + timeDelay; //00:00:00.00000000

        if (strTimeDelay.length() <= LENGTH_TIME) {
            txtTime2.setText(strTimeDelay);
        } else {
            txtTime2.setText(strTimeDelay.substring(0, LENGTH_TIME - 1));
        }
    }
    
    //Copy nội dung vào Clipboard
    private void btnCopyClick(String noiDung){
        StringSelection stringSelection = new StringSelection(noiDung);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }
    
    //Chuyển nội dung thành 1 mảng byte[]
    private byte[] getByte(AESAlgorithm aes, JComboBox<String> cbType, String noiDung){
        switch (cbType.getSelectedIndex()) {
            case TYPE_HEX -> {
                return aes.decodeHexString(noiDung); //Chuyển chuỗi Hex thành mảng Hex[]
            }
            case TYPE_UTF8 -> {
                return noiDung.getBytes(StandardCharsets.UTF_8); //Chuyển chuỗi đầu vào sang byte[]
            }
        }
        return null;
    }
    
    //Xác định phiên bản
    private int getVer(byte[] b) {
        if (b.length % 16 == 0) {
            return b.length / 16;
        } else {
            return b.length / 16 + 1;
        }
    }
    
    //Chuyển mảng byte[] thành mảng byte[][][]
    private byte[][][] getByteConvert(int ver, byte[] b) {
        byte[][][] bConvert = new byte[ver][4][4];
        for (int v = 0; v < ver; v++) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (16 * v + 4 * i + j < b.length) {
                        bConvert[v][j][i] = b[16 * v + 4 * i + j];
                    } else {
                        bConvert[v][j][i] = (byte) 0x09; //Thêm padding
                    }
                }
            }
        }
        return bConvert;
    }
    
    //Xác định chuỗi mã hóa
    private String getStrEncode(AESAlgorithm aes, int ver, int[] byteKeyExpansion, JComboBox<String> cbType, byte[][][] bConvert) {
        String str = "";
        byte[][][] b = new byte[ver][4][4];
        switch (cbType.getSelectedIndex()) {
            case TYPE_HEX -> {
                //Mã hóa các phiên bản
                for (int v = 0; v < ver; v++) {
                    b[v] = aes.cipher(bConvert[v], byteKeyExpansion); //Mã hóa phiên bản v

                    //Chuyển mảng byte thành chuỗi hex
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < 4; i++) {
                        for (int j = 0; j < 4; j++) {
                            sb.append(String.format("%02X", b[v][j][i]));
                        }
                    }
                    str += sb.toString();
                }
            }
            case TYPE_UTF8 -> {
                byte[] byteOutConvert = new byte[ver * 4 * 4];
                //Mã hóa các phiên bản
                for (int v = 0; v < ver; v++) {
                    b[v] = aes.cipher(bConvert[v], byteKeyExpansion); //Mã hóa phiên bản v

                    for (int i = 0; i < 4; i++) {
                        for (int j = 0; j < 4; j++) {
                            byteOutConvert[16 * v + 4 * i + j] = b[v][j][i];
                        }
                    }
                    //Chuyển mảng byte thành chuỗi UTF-8
                    str = new String(byteOutConvert, StandardCharsets.UTF_8);
                }
            }
        }
        return str;
    }
    
    //Xác định chuỗi giải mã
    private String getStrDecode(AESAlgorithm aes, int ver, int[] byteKeyExpansion, JComboBox<String> cbType, byte[][][] bConvert) {
        String str = "";
        byte[][][] b = new byte[ver][4][4];
        ArrayList<Byte> byteConvert = new ArrayList<>();
        byte[] byteConvert2 = null;
        switch (cbType.getSelectedIndex()) {
            case TYPE_HEX -> {
                //Giải mã các phiên bản
                for (int v = 0; v < ver; v++) {
                    b[v] = aes.invCipher(bConvert[v], byteKeyExpansion); //Giải mã phiên bản v
                    
                    //Chuyển sang mảng 1 chiều
                    for (int i = 0; i < 4; i++) {
                        for (int j = 0; j < 4; j++) {
                            byteConvert.add(b[v][j][i]);
                        }
                    }
                }
                
                byteConvert2 = arrayListToArray(byteConvert); //Chuyển từ ArrayList sang array

                //Chuyển mảng byte thành chuỗi hex
                StringBuilder sb = new StringBuilder();
                for (byte b1 : byteConvert2) {
                    sb.append(String.format("%02X", b1));
                }
                str = sb.toString();
            }
            case TYPE_UTF8 -> {
                //Giải mã các phiên bản
                for (int v = 0; v < ver; v++) {
                    b[v] = aes.invCipher(bConvert[v], byteKeyExpansion); //Giải mã phiên bản v

                    //Chuyển sang mảng 1 chiều
                    for (int i = 0; i < 4; i++) {
                        for (int j = 0; j < 4; j++) {
                            byteConvert.add(b[v][j][i]);
                        }
                    }
                }
                
                byteConvert2 = arrayListToArray(byteConvert); //Chuyển từ ArrayList sang array
                
                //Chuyển mảng byte thành chuỗi UTF-8
                str = new String(byteConvert2, StandardCharsets.UTF_8);
            }
        }
        return str;
    }
    
    //Chuyển ArrayList thành array
    private byte[] arrayListToArray(ArrayList<Byte> arrayList) {
        byte[] array = null;
        
        //Loại bỏ các padding phía sau
        for (int i = arrayList.size() - 1; i >= 0; i--) {
            if (arrayList.get(i) == (byte) 0x09) {
                arrayList.remove(i);
            } else {
                array = new byte[i + 1];
                break;
            }
        }
        
        //Khởi tạo array
        for (int i = 0; i < array.length; i++) {
            array[i] = arrayList.get(i);
        }
        return array;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cbbSizeKey = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        txtLoopNumber = new javax.swing.JTextField();
        btnExit = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cbbTypePlain1 = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtPlain1 = new javax.swing.JTextArea();
        cbbTypeKey1 = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        txtKey1 = new javax.swing.JTextField();
        cbbTypeCipher1 = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        btnEncode = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        txtTime1 = new javax.swing.JTextField();
        txtNotiKey1 = new javax.swing.JLabel();
        btnReset1 = new javax.swing.JButton();
        btnCopy1 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtCipher1 = new javax.swing.JTextArea();
        txtNotiPlain1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cbbTypeCipher2 = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtCipher2 = new javax.swing.JTextArea();
        cbbTypeKey2 = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        txtKey2 = new javax.swing.JTextField();
        cbbTypePlain2 = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        btnDecode = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        txtTime2 = new javax.swing.JTextField();
        txtNotiKey2 = new javax.swing.JLabel();
        btnReset2 = new javax.swing.JButton();
        btnCopy2 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtPlain2 = new javax.swing.JTextArea();
        txtNotiCipher2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Mã hóa và giải mã AES");
        setBackground(new java.awt.Color(102, 255, 255));
        setResizable(false);

        jPanel3.setForeground(new java.awt.Color(102, 255, 255));

        jLabel1.setText("Độ dài khóa:");

        cbbSizeKey.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "128", "192", "256" }));

        jLabel2.setText("Số lần lặp:");

        txtLoopNumber.setText("10");
        txtLoopNumber.setEnabled(false);

        btnExit.setText("Thoát");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbbSizeKey, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtLoopNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cbbSizeKey, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtLoopNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExit))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 0, 0));
        jLabel3.setText("Mã hóa");

        jLabel4.setText("Bản rõ:");

        cbbTypePlain1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hex", "UTF-8" }));

        txtPlain1.setColumns(20);
        txtPlain1.setLineWrap(true);
        txtPlain1.setRows(3);
        jScrollPane1.setViewportView(txtPlain1);

        cbbTypeKey1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hex", "UTF-8" }));

        jLabel5.setText("Khóa:");

        cbbTypeCipher1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hex", "UTF-8" }));

        jLabel6.setText("Bản mã:");

        btnEncode.setText("Mã hóa");

        jLabel11.setText("Thời gian (s):");

        txtTime1.setText("00:00:00.00");
        txtTime1.setEnabled(false);

        txtNotiKey1.setFont(new java.awt.Font("Dialog", 3, 11)); // NOI18N
        txtNotiKey1.setForeground(new java.awt.Color(255, 51, 51));
        txtNotiKey1.setText(" ");

        btnReset1.setText("Reset");

        btnCopy1.setText("Sao chép");

        txtCipher1.setColumns(20);
        txtCipher1.setLineWrap(true);
        txtCipher1.setRows(3);
        txtCipher1.setEnabled(false);
        jScrollPane3.setViewportView(txtCipher1);

        txtNotiPlain1.setFont(new java.awt.Font("Dialog", 3, 11)); // NOI18N
        txtNotiPlain1.setForeground(new java.awt.Color(255, 51, 51));
        txtNotiPlain1.setText(" ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addGap(868, 868, 868))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cbbTypeKey1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(cbbTypeCipher1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 499, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtKey1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
                                    .addComponent(txtNotiKey1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbbTypePlain1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 499, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtNotiPlain1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 499, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnCopy1, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                            .addComponent(btnReset1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtTime1)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnEncode, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnReset1)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(cbbTypePlain1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtNotiPlain1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtKey1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbbTypeKey1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNotiKey1))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnEncode)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTime1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbbTypeCipher1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCopy1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 51, 255));
        jLabel7.setText("Giải mã");

        jLabel8.setText("Bản mã:");

        cbbTypeCipher2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hex", "UTF-8" }));

        txtCipher2.setColumns(20);
        txtCipher2.setLineWrap(true);
        txtCipher2.setRows(3);
        jScrollPane2.setViewportView(txtCipher2);

        cbbTypeKey2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hex", "UTF-8" }));

        jLabel9.setText("Khóa:");

        cbbTypePlain2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hex", "UTF-8" }));

        jLabel10.setText("Bản rõ:");

        btnDecode.setText("Giải mã");

        jLabel19.setText("Thời gian (s):");

        txtTime2.setText("00:00:00.00");
        txtTime2.setEnabled(false);

        txtNotiKey2.setFont(new java.awt.Font("Dialog", 3, 11)); // NOI18N
        txtNotiKey2.setForeground(new java.awt.Color(255, 51, 51));
        txtNotiKey2.setText(" ");
        txtNotiKey2.setToolTipText("");

        btnReset2.setText("Reset");

        btnCopy2.setText("Sao chép");

        txtPlain2.setColumns(20);
        txtPlain2.setLineWrap(true);
        txtPlain2.setRows(3);
        txtPlain2.setEnabled(false);
        jScrollPane4.setViewportView(txtPlain2);

        txtNotiCipher2.setFont(new java.awt.Font("Dialog", 3, 11)); // NOI18N
        txtNotiCipher2.setForeground(new java.awt.Color(255, 51, 51));
        txtNotiCipher2.setText(" ");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel7)
                        .addGap(868, 868, 868))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cbbTypePlain2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cbbTypeKey2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(19, 19, 19)))
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 499, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtKey2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
                                    .addComponent(txtNotiKey2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbbTypeCipher2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtNotiCipher2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 499, Short.MAX_VALUE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnCopy2, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnReset2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnDecode, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtTime2))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(cbbTypeCipher2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReset2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNotiCipher2)
                    .addComponent(btnDecode))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtKey2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbbTypeKey2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNotiKey2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cbbTypePlain2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel10))
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTime2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCopy2)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 780, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AESForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AESForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AESForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AESForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new AESForm().setVisible(true);
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCopy1;
    private javax.swing.JButton btnCopy2;
    private javax.swing.JButton btnDecode;
    private javax.swing.JButton btnEncode;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnReset1;
    private javax.swing.JButton btnReset2;
    private javax.swing.JComboBox<String> cbbSizeKey;
    private javax.swing.JComboBox<String> cbbTypeCipher1;
    private javax.swing.JComboBox<String> cbbTypeCipher2;
    private javax.swing.JComboBox<String> cbbTypeKey1;
    private javax.swing.JComboBox<String> cbbTypeKey2;
    private javax.swing.JComboBox<String> cbbTypePlain1;
    private javax.swing.JComboBox<String> cbbTypePlain2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextArea txtCipher1;
    private javax.swing.JTextArea txtCipher2;
    private javax.swing.JTextField txtKey1;
    private javax.swing.JTextField txtKey2;
    private javax.swing.JTextField txtLoopNumber;
    private javax.swing.JLabel txtNotiCipher2;
    private javax.swing.JLabel txtNotiKey1;
    private javax.swing.JLabel txtNotiKey2;
    private javax.swing.JLabel txtNotiPlain1;
    private javax.swing.JTextArea txtPlain1;
    private javax.swing.JTextArea txtPlain2;
    private javax.swing.JTextField txtTime1;
    private javax.swing.JTextField txtTime2;
    // End of variables declaration//GEN-END:variables
}
