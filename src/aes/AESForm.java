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
            }else if(e.getSource().equals(btnResetBitChange)){
                btnResetBitChangeClick();
            }else if(e.getSource().equals(btnCalculate)){
                btnCalculateClick();
            }
        }
    };
    /**
     * Creates new form AESForm
     */
    public AESForm() {
        initComponents();

        //?????t icon
        Image icon = Toolkit.getDefaultToolkit().getImage(URL_ICON);
        setIconImage(icon);

        //?????t form ra gi???a
        setLocationRelativeTo(null);

        //g??n s??? ki???n
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
        btnCalculate.addActionListener(myClick);
        btnResetBitChange.addActionListener(myClick);
    }

    //C???p nh???t c???nh b??o kh??a
    private void updateNotiKey(JComboBox cbbTypeKey, JTextField txtKey, JLabel txtNotiKey) {
        int sizeKey = Integer.parseInt(cbbSizeKey.getSelectedItem().toString());
        int lengKey = sizeKey/8;

        switch (cbbTypeKey.getSelectedIndex()) {
            case TYPE_HEX -> {
                if (txtKey.getText().length() == 0 || txtKey.getText().length() == lengKey * 2) {
                    txtNotiKey.setText(" ");
                } else if (txtKey.getText().length() < lengKey * 2) {
                    txtNotiKey.setText("????? d??i kh??a ch??a ?????");
                } else if (txtKey.getText().length() > lengKey * 2) {
                    txtNotiKey.setText("????? d??i kh??a v?????t qu??");
                }
            }
            case TYPE_UTF8 -> {
                int length = txtKey.getText().getBytes(StandardCharsets.UTF_8).length;
                if (length == 0 || length == lengKey) {
                    txtNotiKey.setText(" ");
                } else if (length < lengKey) {
                    txtNotiKey.setText("????? d??i kh??a ch??a ?????");
                } else if (length > lengKey) {
                    txtNotiKey.setText("????? d??i kh??a v?????t qu??");
                }
            }
        }
    }
    
    //Thay ?????i k??ch th?????c kh??a
    private void cbbSizeKeyClick(){
        //C???p nh???t c???nh b??o kh??a
        updateNotiKey(cbbTypeKey1, txtKey1, txtNotiKey1);
        updateNotiKey(cbbTypeKey2, txtKey2, txtNotiKey2);
        
        //Hi???n th??? s??? v??ng l???p
        String sizeKey = cbbSizeKey.getSelectedItem().toString();
        switch (sizeKey) {
            case KEY_SIZE_128 -> txtLoopNumber.setText(LOOP_NUMBER_10);
            case KEY_SIZE_192 -> txtLoopNumber.setText(LOOP_NUMBER_12);
            case KEY_SIZE_256 -> txtLoopNumber.setText(LOOP_NUMBER_14);
        }
    }
    
    //Tho??t ch????ng tr??nh
    private void btnExitClick(){
        System.exit(0); 
    }
    
    //Reset form M?? h??a
    private void btnReset1Click(){
        txtCipher1.setText("");
        txtPlain1.setText("");
        txtKey1.setText("");
        txtTime1.setText("0.000000000");
        txtNotiKey1.setText(" ");

        cbbTypeCipher1.setSelectedIndex(0);
        cbbTypePlain1.setSelectedIndex(0);
        cbbTypeKey1.setSelectedIndex(0);
    }
    
    //Th???c hi???n m?? h??a
    private void btnEncodeClick(){
        //C???p nh???t c???nh b??o        
        txtNotiPlain1.setText(" ");        
        if(txtPlain1.getText().isEmpty()){
            txtNotiPlain1.setText("Vui l??ng nh???p b???n r??!");
            return;
        }else if(txtKey1.getText().isEmpty()){
            txtNotiKey1.setText("Vui l??ng nh???p kh??a");
            return;
        }
        
        LocalTime timeBegin, timeEnd, timeDelay; //T??nh th???i gian th???c thi
        timeBegin = LocalTime.now(); //L???y th???i gian b???t ?????u th???c thi

        int size = 0; //K??ch th?????c kh??a
        int ver = 0; //S??? phi??n b???n

        String strCipher = ""; //Chu???i ???? m?? h??a

        byte[] byteKey = null; //M???ng kh??a
        int[] byteKeyExpansion; //M???ng kh??a m??? r???ng

        byte[] bytePlain = null; //M???ng byte chuy???n t??? chu???i b???n r?? ?????u v??o
        byte[][][] bytePlainConvert; //M???ng byte chuy???n t??? m???ng bytePlain

        size = Integer.parseInt(cbbSizeKey.getSelectedItem().toString()); //L???y k??ch th?????c kh??a

        AESAlgorithm aes = new AESAlgorithm(size);

        //B???t l???i kh??ng ????ng ?????nh d???ng
        try {
            bytePlain = getByte(aes, cbbTypePlain1, txtPlain1.getText()); //Chuy???n chu???i b???n r?? th??nh m???ng byte[]
        } catch (Exception e) {
            txtNotiPlain1.setText(e.getMessage());
            return;
        }
        
        ver = getVer(bytePlain); //X??c ?????nh s??? phi??n b???n
                
        //B???t l???i kh??ng ????ng ?????nh d???ng
        try {
            byteKey = getByte(aes, cbbTypeKey1, txtKey1.getText()); //Chuy???n chu???i kh??a th??nh m???ng byte[]
        } catch (Exception e) {
            txtNotiKey1.setText(e.getMessage());
            return;
        }
        
        //Ki???m tra ????? d??i kh??a
        if(byteKey.length != size/8){
            return;
        }
        
        byteKeyExpansion = aes.createKeyExpansion(byteKey); //T???o kh??a m??? r???ng

        bytePlainConvert = getByteConvert(ver, bytePlain); //Chuy???n m?? th??nh nhi???u phi??n b???n byte[][]
        
        strCipher = getStrEncode(aes, ver, byteKeyExpansion, cbbTypeCipher1, bytePlainConvert); //Chu???i ???? m?? h??a

        txtCipher1.setText(strCipher);

        timeEnd = LocalTime.now(); //l???y th???i gian k???t th??c th???c thi

        //T??nh kho???ng th???i gian th???c thi
        timeDelay = timeEnd.minusHours(timeBegin.getHour())
                .minusMinutes(timeBegin.getMinute())
                .minusSeconds(timeBegin.getSecond())
                .minusNanos(timeBegin.getNano());
        
        String strTimeDelay = "" + timeDelay; //00:00:00.00000000

        if (strTimeDelay.length() <= 6){
            txtTime1.setText("0.000000000");
        } else {
            txtTime1.setText(strTimeDelay.substring(7));
        }
    }
    
    //Reset form Gi???i m??
    private void btnReset2Click(){
        txtCipher2.setText("");
        txtPlain2.setText("");
        txtKey2.setText("");
        txtTime2.setText("0.000000000");
        txtNotiKey2.setText(" ");

        cbbTypeCipher2.setSelectedIndex(0);
        cbbTypePlain2.setSelectedIndex(0);
        cbbTypeKey2.setSelectedIndex(0);
    }
    
    //Th???c hi???n gi???i m??
    private void btnDecodeClick(){
        //C???p nh???t c???nh b??o
        txtNotiCipher2.setText(" ");
        if(txtCipher2.getText().isEmpty()){
            txtNotiCipher2.setText("Vui l??ng nh???p b???n m??!");
            return;
        }else if(txtKey2.getText().isEmpty()){
            txtNotiKey2.setText("Vui l??ng nh???p kh??a");
            return;
        }
        
        LocalTime timeBegin, timeEnd, timeDelay; //T??nh th???i gian th???c thi
        timeBegin = LocalTime.now(); //L???y th???i gian b???t ?????u th???c thi

        int size = 0; //K??ch th?????c kh??a
        int ver = 0; //S??? phi??n b???n

        String strPlain = ""; //Chu???i ???? gi???i m??

        byte[] byteKey = null; //M???ng kh??a
        int[] byteKeyExpansion; //M???ng kh??a m??? r???ng

        byte[] byteCipher = null; //M???ng byte chuy???n t??? chu???i b???n m?? ?????u v??o
        byte[][][] byteCipherConvert; //M???ng byte chuy???n t??? m???ng byteCipher

        size = Integer.parseInt(cbbSizeKey.getSelectedItem().toString()); //L???y k??ch th?????c kh??a

        AESAlgorithm aes = new AESAlgorithm(size);

        //B???t l???i kh??ng ????ng ?????nh d???ng
        try {
            byteCipher = getByte(aes, cbbTypeCipher2, txtCipher2.getText()); //Chuy???n chu???i b???n m?? th??nh m???ng byte[]
        } catch (Exception e) {
            txtNotiCipher2.setText(e.getMessage());
            return;
        }
        
        //Ki???m tra b???n m?? ???? ????? k?? t??? ch??a
        if(byteCipher.length%16!=0){
            txtNotiCipher2.setText("B???n m?? kh??ng h???p l???");
            return;
        }
        
        ver = getVer(byteCipher); //X??c ?????nh s??? phi??n b???n

        try {
            byteKey = getByte(aes, cbbTypeKey2, txtKey2.getText()); //Chuy???n chu???i key th??nh m???ng byte[]
        } catch (Exception e) {
            txtNotiKey2.setText(e.getMessage());
            return;
        }
        
        if(byteKey.length != size/8){
            return;
        }
        
        byteKeyExpansion = aes.createKeyExpansion(byteKey); //T???o kh??a m??? r???ng
        
        byteCipherConvert = getByteConvert(ver, byteCipher); //Chuy???n m?? th??nh nhi???u phi??n b???n byte[][]

        strPlain = getStrDecode(aes, ver, byteKeyExpansion, cbbTypePlain2, byteCipherConvert); //Chu???i ???? gi???i m??

        txtPlain2.setText(strPlain);

        timeEnd = LocalTime.now(); //l???y th???i gian k???t th??c th???c thi

        //T??nh kho???ng th???i gian th???c thi
        timeDelay = timeEnd.minusHours(timeBegin.getHour())
                .minusMinutes(timeBegin.getMinute())
                .minusSeconds(timeBegin.getSecond())
                .minusNanos(timeBegin.getNano());

        String strTimeDelay = "" + timeDelay; //00:00:00.0000000

        if (strTimeDelay.length() <= 6){
            txtTime2.setText("00.000000000");
        } else {
            txtTime2.setText(strTimeDelay.substring(7));
        }
    }
    
    //Copy n???i dung v??o Clipboard
    private void btnCopyClick(String noiDung){
        StringSelection stringSelection = new StringSelection(noiDung);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }
    
    //Reset form t??nh bit thay ?????i
    private void btnResetBitChangeClick(){
        txtHex1.setText("");
        txtHex2.setText("");
        txtNotiHex1.setText(" ");
        txtNotiHex2.setText(" ");
        txtBitChange.setText("0");
    }

    //T??nh s??? bit thay ?????i
    private void btnCalculateClick(){
       //C???p nh???t c???nh b??o
        txtNotiHex1.setText(" ");
        txtNotiHex2.setText(" ");
        if(txtHex1.getText().trim().isEmpty()){
            txtNotiHex1.setText("Vui l??ng nh???p b???n 1!");
            return;
        }else if(txtHex2.getText().trim().isEmpty()){
            txtNotiHex2.setText("Vui l??ng nh???p b???n 1!");
            return;
        }else if(txtHex1.getText().trim().length() != txtHex2.getText().trim().length()){
            txtNotiHex1.setText("2 b???n c?? ????? d??i kh??c nhau!");
            txtNotiHex2.setText("2 b???n c?? ????? d??i kh??c nhau!");
            return;
        }
        
        AESAlgorithm aes = new AESAlgorithm();
        
        String strHex1 = txtHex1.getText();
        String strHex2 = txtHex2.getText();
        
        byte[] byteHex1 = aes.decodeHexString(strHex1);  //Chuy???n chu???i Hex th??nh m???ng Hex[]
        byte[] byteHex2 = aes.decodeHexString(strHex2);  //Chuy???n chu???i Hex th??nh m???ng Hex[]
        
        String strBit1 = "", strBit2 = "";
        
        for(byte b : byteHex1){
            strBit1 += String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(" ", "0");
        }
        
        for(byte b : byteHex2){
            strBit2 += String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(" ", "0");
        }
        
        int count = 0; //S??? bit thay ?????i
        for(int i = 0; i < strBit1.length(); i++){
            if(strBit1.charAt(i) != strBit2.charAt(i)){
                count++;
            }
        }
        txtBitChange.setText(""+count);
    }
    
    //Chuy???n n???i dung th??nh 1 m???ng byte[]
    private byte[] getByte(AESAlgorithm aes, JComboBox<String> cbType, String noiDung){
        switch (cbType.getSelectedIndex()) {
            case TYPE_HEX -> {
                return aes.decodeHexString(noiDung); //Chuy???n chu???i Hex th??nh m???ng Hex[]
            }
            case TYPE_UTF8 -> {
                return noiDung.getBytes(StandardCharsets.UTF_8); //Chuy???n chu???i ?????u v??o sang byte[]
            }
        }
        return null;
    }
    
    //X??c ?????nh phi??n b???n
    private int getVer(byte[] b) {
        if (b.length % 16 == 0) {
            return b.length / 16;
        } else {
            return b.length / 16 + 1;
        }
    }
    
    //Chuy???n m???ng byte[] th??nh m???ng byte[][][]
    private byte[][][] getByteConvert(int ver, byte[] b) {
        byte[][][] bConvert = new byte[ver][4][4];
        for (int v = 0; v < ver; v++) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (16 * v + 4 * i + j < b.length) {
                        bConvert[v][j][i] = b[16 * v + 4 * i + j];
                    } else {
                        bConvert[v][j][i] = (byte) 0x09; //Th??m padding
                    }
                }
            }
        }
        return bConvert;
    }
    
    //X??c ?????nh chu???i m?? h??a
    private String getStrEncode(AESAlgorithm aes, int ver, int[] byteKeyExpansion, JComboBox<String> cbType, byte[][][] bConvert) {
        String str = "";
        byte[][][] b = new byte[ver][4][4];
        switch (cbType.getSelectedIndex()) {
            case TYPE_HEX -> {
                //M?? h??a c??c phi??n b???n
                for (int v = 0; v < ver; v++) {
                    b[v] = aes.cipher(bConvert[v], byteKeyExpansion); //M?? h??a phi??n b???n v

                    //Chuy???n m???ng byte th??nh chu???i hex
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
                //M?? h??a c??c phi??n b???n
                for (int v = 0; v < ver; v++) {
                    b[v] = aes.cipher(bConvert[v], byteKeyExpansion); //M?? h??a phi??n b???n v

                    for (int i = 0; i < 4; i++) {
                        for (int j = 0; j < 4; j++) {
                            byteOutConvert[16 * v + 4 * i + j] = b[v][j][i];
                        }
                    }
                    //Chuy???n m???ng byte th??nh chu???i UTF-8
                    str = new String(byteOutConvert, StandardCharsets.UTF_8);
                }
            }
        }
        return str;
    }
    
    //X??c ?????nh chu???i gi???i m??
    private String getStrDecode(AESAlgorithm aes, int ver, int[] byteKeyExpansion, JComboBox<String> cbType, byte[][][] bConvert) {
        String str = "";
        byte[][][] b = new byte[ver][4][4];
        ArrayList<Byte> byteConvert = new ArrayList<>();
        byte[] byteConvert2 = null;
        switch (cbType.getSelectedIndex()) {
            case TYPE_HEX -> {
                //Gi???i m?? c??c phi??n b???n
                for (int v = 0; v < ver; v++) {
                    b[v] = aes.invCipher(bConvert[v], byteKeyExpansion); //Gi???i m?? phi??n b???n v
                    
                    //Chuy???n sang m???ng 1 chi???u
                    for (int i = 0; i < 4; i++) {
                        for (int j = 0; j < 4; j++) {
                            byteConvert.add(b[v][j][i]);
                        }
                    }
                }
                
                byteConvert2 = arrayListToArray(byteConvert); //Chuy???n t??? ArrayList sang array

                //Chuy???n m???ng byte th??nh chu???i hex
                StringBuilder sb = new StringBuilder();
                for (byte b1 : byteConvert2) {
                    sb.append(String.format("%02X", b1));
                }
                str = sb.toString();
            }
            case TYPE_UTF8 -> {
                //Gi???i m?? c??c phi??n b???n
                for (int v = 0; v < ver; v++) {
                    b[v] = aes.invCipher(bConvert[v], byteKeyExpansion); //Gi???i m?? phi??n b???n v

                    //Chuy???n sang m???ng 1 chi???u
                    for (int i = 0; i < 4; i++) {
                        for (int j = 0; j < 4; j++) {
                            byteConvert.add(b[v][j][i]);
                        }
                    }
                }
                
                byteConvert2 = arrayListToArray(byteConvert); //Chuy???n t??? ArrayList sang array
                
                //Chuy???n m???ng byte th??nh chu???i UTF-8
                str = new String(byteConvert2, StandardCharsets.UTF_8);
            }
        }
        return str;
    }
    
    //Chuy???n ArrayList th??nh array
    private byte[] arrayListToArray(ArrayList<Byte> arrayList) {
        byte[] array = null;
        
        //Lo???i b??? c??c padding ph??a sau
        for (int i = arrayList.size() - 1; i >= 0; i--) {
            if (arrayList.get(i) == (byte) 0x09) {
                arrayList.remove(i);
            } else {
                array = new byte[i + 1];
                break;
            }
        }
        
        //Kh???i t???o array
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
        btnExit = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtHex1 = new javax.swing.JTextArea();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        txtHex2 = new javax.swing.JTextArea();
        btnCalculate = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        txtBitChange = new javax.swing.JTextField();
        txtNotiHex1 = new javax.swing.JLabel();
        txtNotiHex2 = new javax.swing.JLabel();
        btnResetBitChange = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("M?? h??a v?? gi???i m?? AES");
        setBackground(new java.awt.Color(102, 255, 255));
        setResizable(false);

        jPanel3.setForeground(new java.awt.Color(102, 255, 255));

        jLabel1.setText("????? d??i kh??a:");

        cbbSizeKey.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "128", "192", "256" }));

        jLabel2.setText("S??? l???n l???p:");

        txtLoopNumber.setEditable(false);
        txtLoopNumber.setText("10");

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
                .addContainerGap(543, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cbbSizeKey, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtLoopNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 0, 0));
        jLabel3.setText("M?? h??a");

        jLabel4.setText("B???n r??:");

        cbbTypePlain1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hex", "UTF-8" }));

        txtPlain1.setColumns(20);
        txtPlain1.setLineWrap(true);
        txtPlain1.setRows(8);
        jScrollPane1.setViewportView(txtPlain1);
        txtPlain1.getAccessibleContext().setAccessibleParent(jLabel2);

        cbbTypeKey1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hex", "UTF-8" }));

        jLabel5.setText("Kh??a:");

        cbbTypeCipher1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hex", "UTF-8" }));
        cbbTypeCipher1.setEnabled(false);

        jLabel6.setText("B???n m??:");

        btnEncode.setText("M?? h??a");

        jLabel11.setText("Th???i gian (s):");

        txtTime1.setEditable(false);
        txtTime1.setText("0.000000000");

        txtNotiKey1.setFont(new java.awt.Font("Dialog", 3, 11)); // NOI18N
        txtNotiKey1.setForeground(new java.awt.Color(255, 51, 51));
        txtNotiKey1.setText(" ");

        btnReset1.setText("Reset");

        btnCopy1.setText("Sao ch??p");

        txtCipher1.setEditable(false);
        txtCipher1.setColumns(20);
        txtCipher1.setLineWrap(true);
        txtCipher1.setRows(8);
        jScrollPane3.setViewportView(txtCipher1);
        txtCipher1.getAccessibleContext().setAccessibleParent(jLabel2);

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
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtNotiKey1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtKey1)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 431, Short.MAX_VALUE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(133, 133, 133)
                                .addComponent(txtNotiPlain1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(6, 6, 6)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnEncode, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTime1, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCopy1))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbbTypePlain1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnReset1, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(300, 300, 300))))
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
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNotiKey1)
                            .addComponent(txtTime1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnEncode)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCopy1))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cbbTypeCipher1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel6))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 0, 0));
        jLabel7.setText("Gi???i m??");

        jLabel8.setText("B???n m??:");

        cbbTypeCipher2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hex", "UTF-8" }));
        cbbTypeCipher2.setEnabled(false);

        txtCipher2.setColumns(20);
        txtCipher2.setLineWrap(true);
        txtCipher2.setRows(8);
        jScrollPane2.setViewportView(txtCipher2);

        cbbTypeKey2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hex", "UTF-8" }));

        jLabel9.setText("Kh??a:");

        cbbTypePlain2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hex", "UTF-8" }));

        jLabel10.setText("B???n r??:");

        btnDecode.setText("Gi???i m??");

        jLabel19.setText("Th???i gian (s):");

        txtTime2.setEditable(false);
        txtTime2.setText("0.000000000");

        txtNotiKey2.setFont(new java.awt.Font("Dialog", 3, 11)); // NOI18N
        txtNotiKey2.setForeground(new java.awt.Color(255, 51, 51));
        txtNotiKey2.setText(" ");
        txtNotiKey2.setToolTipText("");

        btnReset2.setText("Reset");

        btnCopy2.setText("Sao ch??p");

        txtPlain2.setEditable(false);
        txtPlain2.setColumns(20);
        txtPlain2.setLineWrap(true);
        txtPlain2.setRows(8);
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
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                                .addComponent(cbbTypePlain2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                                .addComponent(cbbTypeKey2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(txtKey2, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(txtNotiKey2, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTime2, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnCopy2)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbbTypeCipher2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtNotiCipher2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnReset2, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnDecode, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))))
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
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(btnDecode)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel19))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(txtNotiCipher2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtKey2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbbTypeKey2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(txtNotiKey2, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cbbTypePlain2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnCopy2))
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(txtTime2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnExit.setText("Tho??t");

        jPanel4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel12.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(51, 51, 255));
        jLabel12.setText("So s??nh s??? bit kh??c nhau gi???a b???n 1 v?? b???n 2");

        jLabel13.setText("B???n 1:");

        txtHex1.setColumns(20);
        txtHex1.setLineWrap(true);
        txtHex1.setRows(8);
        jScrollPane5.setViewportView(txtHex1);

        jLabel14.setText("B???n 2:");

        txtHex2.setColumns(20);
        txtHex2.setLineWrap(true);
        txtHex2.setRows(8);
        jScrollPane6.setViewportView(txtHex2);

        btnCalculate.setText("T??nh");

        jLabel15.setText("S??? bit thay ?????i:");

        txtBitChange.setEditable(false);
        txtBitChange.setText("0");

        txtNotiHex1.setFont(new java.awt.Font("Dialog", 3, 11)); // NOI18N
        txtNotiHex1.setForeground(new java.awt.Color(255, 51, 51));
        txtNotiHex1.setText(" ");

        txtNotiHex2.setFont(new java.awt.Font("Dialog", 3, 11)); // NOI18N
        txtNotiHex2.setForeground(new java.awt.Color(255, 51, 51));
        txtNotiHex2.setText(" ");

        btnResetBitChange.setText("Reset");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(txtNotiHex1, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(txtNotiHex2, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(186, 186, 186))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel12))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(89, 89, 89)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnCalculate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtBitChange, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnResetBitChange, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addComponent(btnResetBitChange)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnCalculate)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel15)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtBitChange, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNotiHex1)
                    .addComponent(txtNotiHex2))
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
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 680, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 680, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 7, Short.MAX_VALUE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExit))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
    private javax.swing.JButton btnCalculate;
    private javax.swing.JButton btnCopy1;
    private javax.swing.JButton btnCopy2;
    private javax.swing.JButton btnDecode;
    private javax.swing.JButton btnEncode;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnReset1;
    private javax.swing.JButton btnReset2;
    private javax.swing.JButton btnResetBitChange;
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
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
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
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTextField txtBitChange;
    private javax.swing.JTextArea txtCipher1;
    private javax.swing.JTextArea txtCipher2;
    private javax.swing.JTextArea txtHex1;
    private javax.swing.JTextArea txtHex2;
    private javax.swing.JTextField txtKey1;
    private javax.swing.JTextField txtKey2;
    private javax.swing.JTextField txtLoopNumber;
    private javax.swing.JLabel txtNotiCipher2;
    private javax.swing.JLabel txtNotiHex1;
    private javax.swing.JLabel txtNotiHex2;
    private javax.swing.JLabel txtNotiKey1;
    private javax.swing.JLabel txtNotiKey2;
    private javax.swing.JLabel txtNotiPlain1;
    private javax.swing.JTextArea txtPlain1;
    private javax.swing.JTextArea txtPlain2;
    private javax.swing.JTextField txtTime1;
    private javax.swing.JTextField txtTime2;
    // End of variables declaration//GEN-END:variables
}
