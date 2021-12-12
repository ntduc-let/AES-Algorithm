package aes;

/**
 *
 * @author ntduc-let
 */
public class AESAlgorithm {    
    public static final int KEY_SIZE_128 = 128;
    public static final int KEY_SIZE_192 = 192;
    public static final int KEY_SIZE_256 = 256;
    public static final int NB_VALUE = 4;
    
    protected static int Nk, Nr, Nb = NB_VALUE;
    // AES-128 Nk=4, Nb=4, Nr=10
    // AES-192 Nk=6, Nb=4, Nr=12
    // AES-256 Nk=8, Nb=4, Nr=14
    
    protected static final byte[][] sbox = { 
        {(byte) 0x63, (byte) 0x7c, (byte) 0x77, (byte) 0x7b, (byte) 0xf2, (byte) 0x6b, (byte) 0x6f, (byte) 0xc5, (byte) 0x30, (byte) 0x01, (byte) 0x67, (byte) 0x2b, (byte) 0xfe, (byte) 0xd7, (byte) 0xab, (byte) 0x76},
        {(byte) 0xca, (byte) 0x82, (byte) 0xc9, (byte) 0x7d, (byte) 0xfa, (byte) 0x59, (byte) 0x47, (byte) 0xf0, (byte) 0xad, (byte) 0xd4, (byte) 0xa2, (byte) 0xaf, (byte) 0x9c, (byte) 0xa4, (byte) 0x72, (byte) 0xc0},
        {(byte) 0xb7, (byte) 0xfd, (byte) 0x93, (byte) 0x26, (byte) 0x36, (byte) 0x3f, (byte) 0xf7, (byte) 0xcc, (byte) 0x34, (byte) 0xa5, (byte) 0xe5, (byte) 0xf1, (byte) 0x71, (byte) 0xd8, (byte) 0x31, (byte) 0x15},
        {(byte) 0x04, (byte) 0xc7, (byte) 0x23, (byte) 0xc3, (byte) 0x18, (byte) 0x96, (byte) 0x05, (byte) 0x9a, (byte) 0x07, (byte) 0x12, (byte) 0x80, (byte) 0xe2, (byte) 0xeb, (byte) 0x27, (byte) 0xb2, (byte) 0x75},
        {(byte) 0x09, (byte) 0x83, (byte) 0x2c, (byte) 0x1a, (byte) 0x1b, (byte) 0x6e, (byte) 0x5a, (byte) 0xa0, (byte) 0x52, (byte) 0x3b, (byte) 0xd6, (byte) 0xb3, (byte) 0x29, (byte) 0xe3, (byte) 0x2f, (byte) 0x84},
        {(byte) 0x53, (byte) 0xd1, (byte) 0x00, (byte) 0xed, (byte) 0x20, (byte) 0xfc, (byte) 0xb1, (byte) 0x5b, (byte) 0x6a, (byte) 0xcb, (byte) 0xbe, (byte) 0x39, (byte) 0x4a, (byte) 0x4c, (byte) 0x58, (byte) 0xcf},
        {(byte) 0xd0, (byte) 0xef, (byte) 0xaa, (byte) 0xfb, (byte) 0x43, (byte) 0x4d, (byte) 0x33, (byte) 0x85, (byte) 0x45, (byte) 0xf9, (byte) 0x02, (byte) 0x7f, (byte) 0x50, (byte) 0x3c, (byte) 0x9f, (byte) 0xa8},
        {(byte) 0x51, (byte) 0xa3, (byte) 0x40, (byte) 0x8f, (byte) 0x92, (byte) 0x9d, (byte) 0x38, (byte) 0xf5, (byte) 0xbc, (byte) 0xb6, (byte) 0xda, (byte) 0x21, (byte) 0x10, (byte) 0xff, (byte) 0xf3, (byte) 0xd2},
        {(byte) 0xcd, (byte) 0x0c, (byte) 0x13, (byte) 0xec, (byte) 0x5f, (byte) 0x97, (byte) 0x44, (byte) 0x17, (byte) 0xc4, (byte) 0xa7, (byte) 0x7e, (byte) 0x3d, (byte) 0x64, (byte) 0x5d, (byte) 0x19, (byte) 0x73},
        {(byte) 0x60, (byte) 0x81, (byte) 0x4f, (byte) 0xdc, (byte) 0x22, (byte) 0x2a, (byte) 0x90, (byte) 0x88, (byte) 0x46, (byte) 0xee, (byte) 0xb8, (byte) 0x14, (byte) 0xde, (byte) 0x5e, (byte) 0x0b, (byte) 0xdb},
        {(byte) 0xe0, (byte) 0x32, (byte) 0x3a, (byte) 0x0a, (byte) 0x49, (byte) 0x06, (byte) 0x24, (byte) 0x5c, (byte) 0xc2, (byte) 0xd3, (byte) 0xac, (byte) 0x62, (byte) 0x91, (byte) 0x95, (byte) 0xe4, (byte) 0x79},
        {(byte) 0xe7, (byte) 0xc8, (byte) 0x37, (byte) 0x6d, (byte) 0x8d, (byte) 0xd5, (byte) 0x4e, (byte) 0xa9, (byte) 0x6c, (byte) 0x56, (byte) 0xf4, (byte) 0xea, (byte) 0x65, (byte) 0x7a, (byte) 0xae, (byte) 0x08},
        {(byte) 0xba, (byte) 0x78, (byte) 0x25, (byte) 0x2e, (byte) 0x1c, (byte) 0xa6, (byte) 0xb4, (byte) 0xc6, (byte) 0xe8, (byte) 0xdd, (byte) 0x74, (byte) 0x1f, (byte) 0x4b, (byte) 0xbd, (byte) 0x8b, (byte) 0x8a},
        {(byte) 0x70, (byte) 0x3e, (byte) 0xb5, (byte) 0x66, (byte) 0x48, (byte) 0x03, (byte) 0xf6, (byte) 0x0e, (byte) 0x61, (byte) 0x35, (byte) 0x57, (byte) 0xb9, (byte) 0x86, (byte) 0xc1, (byte) 0x1d, (byte) 0x9e},
        {(byte) 0xe1, (byte) 0xf8, (byte) 0x98, (byte) 0x11, (byte) 0x69, (byte) 0xd9, (byte) 0x8e, (byte) 0x94, (byte) 0x9b, (byte) 0x1e, (byte) 0x87, (byte) 0xe9, (byte) 0xce, (byte) 0x55, (byte) 0x28, (byte) 0xdf},
        {(byte) 0x8c, (byte) 0xa1, (byte) 0x89, (byte) 0x0d, (byte) 0xbf, (byte) 0xe6, (byte) 0x42, (byte) 0x68, (byte) 0x41, (byte) 0x99, (byte) 0x2d, (byte) 0x0f, (byte) 0xb0, (byte) 0x54, (byte) 0xbb, (byte) 0x16}
    };
    
    protected static final byte[][] sboxInv = {
        {(byte) 0x52, (byte) 0x09, (byte) 0x6a, (byte) 0xd5, (byte) 0x30, (byte) 0x36, (byte) 0xa5, (byte) 0x38, (byte) 0xbf, (byte) 0x40, (byte) 0xa3, (byte) 0x9e, (byte) 0x81, (byte) 0xf3, (byte) 0xd7, (byte) 0xfb},
        {(byte) 0x7c, (byte) 0xe3, (byte) 0x39, (byte) 0x82, (byte) 0x9b, (byte) 0x2f, (byte) 0xff, (byte) 0x87, (byte) 0x34, (byte) 0x8e, (byte) 0x43, (byte) 0x44, (byte) 0xc4, (byte) 0xde, (byte) 0xe9, (byte) 0xcb},
        {(byte) 0x54, (byte) 0x7b, (byte) 0x94, (byte) 0x32, (byte) 0xa6, (byte) 0xc2, (byte) 0x23, (byte) 0x3d, (byte) 0xee, (byte) 0x4c, (byte) 0x95, (byte) 0x0b, (byte) 0x42, (byte) 0xfa, (byte) 0xc3, (byte) 0x4e},
        {(byte) 0x08, (byte) 0x2e, (byte) 0xa1, (byte) 0x66, (byte) 0x28, (byte) 0xd9, (byte) 0x24, (byte) 0xb2, (byte) 0x76, (byte) 0x5b, (byte) 0xa2, (byte) 0x49, (byte) 0x6d, (byte) 0x8b, (byte) 0xd1, (byte) 0x25},
        {(byte) 0x72, (byte) 0xf8, (byte) 0xf6, (byte) 0x64, (byte) 0x86, (byte) 0x68, (byte) 0x98, (byte) 0x16, (byte) 0xd4, (byte) 0xa4, (byte) 0x5c, (byte) 0xcc, (byte) 0x5d, (byte) 0x65, (byte) 0xb6, (byte) 0x92},
        {(byte) 0x6c, (byte) 0x70, (byte) 0x48, (byte) 0x50, (byte) 0xfd, (byte) 0xed, (byte) 0xb9, (byte) 0xda, (byte) 0x5e, (byte) 0x15, (byte) 0x46, (byte) 0x57, (byte) 0xa7, (byte) 0x8d, (byte) 0x9d, (byte) 0x84},
        {(byte) 0x90, (byte) 0xd8, (byte) 0xab, (byte) 0x00, (byte) 0x8c, (byte) 0xbc, (byte) 0xd3, (byte) 0x0a, (byte) 0xf7, (byte) 0xe4, (byte) 0x58, (byte) 0x05, (byte) 0xb8, (byte) 0xb3, (byte) 0x45, (byte) 0x06},
        {(byte) 0xd0, (byte) 0x2c, (byte) 0x1e, (byte) 0x8f, (byte) 0xca, (byte) 0x3f, (byte) 0x0f, (byte) 0x02, (byte) 0xc1, (byte) 0xaf, (byte) 0xbd, (byte) 0x03, (byte) 0x01, (byte) 0x13, (byte) 0x8a, (byte) 0x6b},
        {(byte) 0x3a, (byte) 0x91, (byte) 0x11, (byte) 0x41, (byte) 0x4f, (byte) 0x67, (byte) 0xdc, (byte) 0xea, (byte) 0x97, (byte) 0xf2, (byte) 0xcf, (byte) 0xce, (byte) 0xf0, (byte) 0xb4, (byte) 0xe6, (byte) 0x73},
        {(byte) 0x96, (byte) 0xac, (byte) 0x74, (byte) 0x22, (byte) 0xe7, (byte) 0xad, (byte) 0x35, (byte) 0x85, (byte) 0xe2, (byte) 0xf9, (byte) 0x37, (byte) 0xe8, (byte) 0x1c, (byte) 0x75, (byte) 0xdf, (byte) 0x6e},
        {(byte) 0x47, (byte) 0xf1, (byte) 0x1a, (byte) 0x71, (byte) 0x1d, (byte) 0x29, (byte) 0xc5, (byte) 0x89, (byte) 0x6f, (byte) 0xb7, (byte) 0x62, (byte) 0x0e, (byte) 0xaa, (byte) 0x18, (byte) 0xbe, (byte) 0x1b},
        {(byte) 0xfc, (byte) 0x56, (byte) 0x3e, (byte) 0x4b, (byte) 0xc6, (byte) 0xd2, (byte) 0x79, (byte) 0x20, (byte) 0x9a, (byte) 0xdb, (byte) 0xc0, (byte) 0xfe, (byte) 0x78, (byte) 0xcd, (byte) 0x5a, (byte) 0xf4},
        {(byte) 0x1f, (byte) 0xdd, (byte) 0xa8, (byte) 0x33, (byte) 0x88, (byte) 0x07, (byte) 0xc7, (byte) 0x31, (byte) 0xb1, (byte) 0x12, (byte) 0x10, (byte) 0x59, (byte) 0x27, (byte) 0x80, (byte) 0xec, (byte) 0x5f},
        {(byte) 0x60, (byte) 0x51, (byte) 0x7f, (byte) 0xa9, (byte) 0x19, (byte) 0xb5, (byte) 0x4a, (byte) 0x0d, (byte) 0x2d, (byte) 0xe5, (byte) 0x7a, (byte) 0x9f, (byte) 0x93, (byte) 0xc9, (byte) 0x9c, (byte) 0xef},
        {(byte) 0xa0, (byte) 0xe0, (byte) 0x3b, (byte) 0x4d, (byte) 0xae, (byte) 0x2a, (byte) 0xf5, (byte) 0xb0, (byte) 0xc8, (byte) 0xeb, (byte) 0xbb, (byte) 0x3c, (byte) 0x83, (byte) 0x53, (byte) 0x99, (byte) 0x61},
        {(byte) 0x17, (byte) 0x2b, (byte) 0x04, (byte) 0x7e, (byte) 0xba, (byte) 0x77, (byte) 0xd6, (byte) 0x26, (byte) 0xe1, (byte) 0x69, (byte) 0x14, (byte) 0x63, (byte) 0x55, (byte) 0x21, (byte) 0x0c, (byte) 0x7d}
    };
    
    protected static final int Rcon[] = 
        {0x00000000, 0x01000000, 0x02000000, 
        0x04000000, 0x08000000, 0x10000000, 
        0x20000000, 0x40000000, 0x80000000, 
        0x1b000000, 0x36000000};
    
    public AESAlgorithm() {}
    
    public AESAlgorithm(int iBlockLength) {
        if(!isValidKeySize(iBlockLength)){
            System.out.println("Khóa chỉ có độ dài: 128, 192, 256");
        }
    }
    
    //Kiểm tra giá trị kích thước khóa
    public static boolean isValidKeySize(int keySize) {
        switch(keySize) {
            case KEY_SIZE_128 -> {
                Nk = 4;
                Nr = 10;
                return true;
            }
            case KEY_SIZE_192 -> {
                Nk = 6;
                Nr = 12;
                return true;
            }
            case KEY_SIZE_256 -> {
                Nk = 8;
                Nr = 14;
                return true;
            }
            default -> {
                return false;
            }
        }
    }
    
    //Mã hóa
    byte[][] cipher(byte bytesMessage[][], int wordsKeyExpansion[]) {
        byte state[][] = new byte[4][Nb];
        state = bytesMessage;

        //Vòng 0
        state = addRoundKey(state, wordsKeyExpansion, 0);
        
        //Vòng 1 -> Nr-1
        for (int round = 1; round <= Nr - 1; round++) {
            state = subBytes(state);
            state = shiftRows(state);
            state = mixColumns(state);
            state = addRoundKey(state, wordsKeyExpansion, round * Nb);
        }
        
        //Vòng Nr
        state = subBytes(state);
        state = shiftRows(state);
        state = addRoundKey(state, wordsKeyExpansion, Nr * Nb);
        
        return state;
    }
    
    //Lấy cột của state XOR với khóa w tương ứng
    private byte[][] addRoundKey(byte state[][], int w[], int l) {
        byte stateNew[][] = new byte[state.length][state[0].length];
        for (int c = 0; c < Nb; c++) {
            stateNew[0][c] = (byte) (state[0][c] ^ getByte(w[l + c], 3));
            stateNew[1][c] = (byte) (state[1][c] ^ getByte(w[l + c], 2));
            stateNew[2][c] = (byte) (state[2][c] ^ getByte(w[l + c], 1));
            stateNew[3][c] = (byte) (state[3][c] ^ getByte(w[l + c], 0));
        }
        return stateNew;
    }
    
    //return byte thứ iByte của value
    //value (gồm 4 byte) = byte3 byte2 byte1 byte0 
    //iByte = 2 --> return byte2
    private byte getByte(int value, int iByte) {
        return (byte) ((value >>> (iByte * 8)) & 0x000000ff);
    }
    
    //Thay tất cả các giá trị state bằng giá trị trong bảng sbos tương ứng
    //state = 0x1b --> state = sbox[1][b]
    private static byte[][] subBytes(byte state[][]) {
        for (int i = 0; i < state.length; i++)
            for (int j = 0; j < state[i].length; j++)
                state[i][j] = sboxTransform(state[i][j]);
        return state;
    }
    
    //return sbox tương ứng với giá trị value
    //state = 0x1b --> state = sbox[1][b]
    private static byte sboxTransform(byte value) {
        byte bUpper = 0, bLower = 0;
        bUpper = (byte) ((byte) (value >> 4) & 0x0f);
        bLower = (byte) (value & 0x0f);
        return sbox[bUpper][bLower];
    }
    
    //Dịch vòng trái mảng state theo từng hàng
    private byte[][] shiftRows(byte state[][]) {
        byte stateNew[][] = new byte[state.length][state[0].length];
        
        //Hàng 0 thì không shift
        stateNew[0] = state[0];
        
        //Hàng 1 shift vòng trái 1
        //Hàng 2 shift vòng trái 2
        //Hàng 3 shift vòng trái 3
        for (int r = 1; r < state.length; r++)
            for (int c = 0; c < state[r].length; c++)
                stateNew[r][c] = state[r][(c + r) % Nb];
        
        return stateNew;
    }

    //Thực hiện phép nhân hữu hạn
    private byte[][] mixColumns(byte state[][]) {
        byte stateNew[][] = new byte[state.length][state[0].length];
        
        //Lấy từng cột của state nhân hữu hạn với ma trận 
        //02 03 01 01
        //01 02 03 01
        //01 01 02 03
        //03 01 01 02
        for (int c = 0; c < Nb; c++) {
            stateNew[0][c] = xor4Bytes(finiteMultiplication(state[0][c], 0x02), finiteMultiplication(state[1][c], 0x03), state[2][c], state[3][c]);
            stateNew[1][c] = xor4Bytes(state[0][c], finiteMultiplication(state[1][c], 0x02), finiteMultiplication(state[2][c], 0x03), state[3][c]);
            stateNew[2][c] = xor4Bytes(state[0][c], state[1][c], finiteMultiplication(state[2][c], 0x02), finiteMultiplication(state[3][c], 0x03));
            stateNew[3][c] = xor4Bytes(finiteMultiplication(state[0][c], 0x03), state[1][c], state[2][c], finiteMultiplication(state[3][c], 0x02));
        }
        return stateNew;
    }
    
    //Thực hiện xor 4 byte
    // return (0 XOR b1 XOR b2 XOR b3 XOR b4_
    private byte xor4Bytes(byte b1, byte b2, byte b3, byte b4) {
        byte bResult = 0;
        bResult ^= b1;
        bResult ^= b2;
        bResult ^= b3;
        bResult ^= b4;
        return bResult;
    }

    //Phép nhân hữu hạn
    private static byte finiteMultiplication(int v1, int v2) {
        return finiteMultiplication((byte) v1, (byte) v2);
    }

    private static byte finiteMultiplication(byte v1, byte v2) {
        byte bTemps[] = new byte[8];
        byte bResult = 0;
        bTemps[0] = v1;
        for (int i = 1; i < bTemps.length; i++) {
            bTemps[i] = xtime(bTemps[i - 1]);
        }
        for (int i = 0; i < bTemps.length; i++) {
            if (getBit(v2, i) != 1) {
                bTemps[i] = 0;
            }
            bResult ^= bTemps[i];
        }
        return bResult;
    }
    
    //Nếu bit thứ 7 của value = 0 thì return (value << 1)
    //Nếu bit thứ 7 của value = 1 thì return ((value << 1) XOR 1B)
    private static byte xtime(byte value) {
        int iResult = (int) (value & 0x000000ff) * 02; //Dịch trái 1 lần 8 bit đầu của value
        return (byte) (((iResult & 0x100) != 0) ? iResult ^ 0x11b : iResult);
                                 //1 0000 0000              1 0001 1011
    }
    
    //return bit thứ i của value, 0 <= i <= 7
    //value = 1010 0101, i = 3 --> return 0
    private static byte getBit(byte value, int i) {
        final byte bMasks[] = {(byte) 0x01, (byte) 0x02, (byte) 0x04, (byte) 0x08, (byte) 0x10, (byte) 0x20, (byte) 0x40, (byte) 0x80};
                              //00000001  , 00000010   , 00000100   , 00001000   , 00010000   , 00100000   , 01000000   , 10000000
        byte bBit = (byte) (value & bMasks[i]);
        return (byte) ((byte) (bBit >> i) & (byte) 0x01);
    }
    
    //Tạo khóa mở rộng
    public int[] createKeyExpansion(byte key[]) {
        int w[] = new int[Nb * (Nr + 1)];
        keyExpansion(key, w);
        return w;
    }
    
    //Mở rộng key
    public void keyExpansion(byte key[], int w[]) {
        int iTemp = 0;
        int i = 0;

        //Chuyển 4 key (1 byte) liên tiếp thành 1 word (4 byte)
        while (i < Nk) {
            w[i] = toWord(key[4 * i], key[4 * i + 1], key[4 * i + 2], key[4 * i + 3]);
            i++;
        }

        i = Nk;
        
        //Mở rộng thêm word từ w[Nk] đến w[Nb * (Nr + 1) - 1]
        //Với i % Nk
            //Thực hiện rotWord w trước đó (w[i-1])
            //Sau đó thực hiện subWord
            //Sau đó thực hiện XOR Rcon[i/Nk]
            //Sau đó thực hiện XOR w[i-Nk]
        //Với Nk > 6 && i % Nk == 4
            //Thực hiện rotWord w trước đó (w[i-1])
            //Sau đó thực hiện XOR w[i-Nk]
        //Với trường hợp i còn lại
            //Thực hiện XOR w trước đó (w[i-1]) với w[i-Nk]
        while (i < Nb * (Nr + 1)) {
            iTemp = w[i - 1];
            if (i % Nk == 0) {
                iTemp = subWord(rotWord(iTemp)) ^ Rcon[i / Nk];
            } else if (Nk > 6 && i % Nk == 4) {
                iTemp = subWord(iTemp);
            }
            w[i] = w[i - Nk] ^ iTemp;
            i++;
        }
    }
    
    //Chuyển 4 key (1 byte) thành 1 word (4 byte)
    //word = b1 b2 b3 b4
    private static int toWord(byte b1, byte b2, byte b3, byte b4) {
        int word = 0;
        word ^= ((int) b1) << 24;
        word ^= (((int) b2) & 0x000000ff) << 16;
        word ^= (((int) b3) & 0x000000ff) << 8;
        word ^= (((int) b4) & 0x000000ff);
        return word;
    }
    
    //Dịch vòng trái word 1 lần theo byte
    //word = b1 b2 b3 b4 --> word = b2 b3 b4 b1
    private static int rotWord(int word) {
        return (word << 8) ^ ((word >> 24) & 0x000000ff);
    }
    
    //Thay giá trị word bằng giá trị trong bản sbos tương ứng
    //word = 0000 1111 2222 3333 --> word = sbos[00] sbos[00] sbos[11] sbos[11] sbos[22] sbos[22] sbos[33] sbos[33]
    private static int subWord(int word) {
        int newWord = 0;
        newWord ^= (int) sboxTransform((byte) (word >>> 24)) & 0x000000ff;
        newWord <<= 8;
        newWord ^= (int) sboxTransform((byte) ((word & 0xff0000) >>> 16)) & 0x000000ff;
        newWord <<= 8;
        newWord ^= (int) sboxTransform((byte) ((word & 0xff00) >>> 8)) & 0x000000ff;
        newWord <<= 8;
        newWord ^= (int) sboxTransform((byte) (word & 0xff)) & 0x000000ff;
        return newWord;
    }
    
    //Giải mã
    byte[][] invCipher(byte bytesMessage[][], int wordsKeyExpansion[]) {
        byte state[][] = new byte[4][Nb];
        state = bytesMessage;

        //Vòng Nr
        state = addRoundKey(state, wordsKeyExpansion, Nr * Nb);

        //Vòng Nr-1 --> 1
        for (int round = (Nr - 1); round >= 1; round--) {
            state = invShiftRows(state);
            state = invSubBytes(state);
            state = addRoundKey(state, wordsKeyExpansion, round * Nb);
            state = invMixColumns(state);
        }
        
        //Vòng 0
        state = invShiftRows(state);
        state = invSubBytes(state);
        state = addRoundKey(state, wordsKeyExpansion, 0);

        return state;
    }
    
    //Dịch vòng phỉa mảng state theo từng hàng
    private byte[][] invShiftRows(byte state[][]) {
        byte stateNew[][] = new byte[state.length][state[0].length];

        //Hàng 0 thì không shift
        stateNew[0] = state[0];
        
        //Hàng 1 shift vòng phải 1
        //Hàng 2 shift vòng phải 2
        //Hàng 3 shift vòng phải 3
        for (int r = 1; r < state.length; r++)
            for (int c = 0; c < state[r].length; c++)
                stateNew[r][(c + r) % Nb] = state[r][c];

        return stateNew;
    }

    //Thay tất cả các giá trị state bằng giá trị trong bảng invSbox tương ứng
    //state = 0x1b --> state = invSbox[1][b]
    private static byte[][] invSubBytes(byte state[][]) {
        for (int i = 0; i < state.length; i++)
            for (int j = 0; j < state[i].length; j++)
                state[i][j] = invSboxTransform(state[i][j]);
        return state;
    }

    //return invSbox tương ứng với giá trị value
    //state = 0x1b --> state = invSbox[1][b]
    private static byte invSboxTransform(byte value) {
        byte bUpper = 0, bLower = 0;
        bUpper = (byte) ((byte) (value >> 4) & 0x0f);
        bLower = (byte) (value & 0x0f);
        return sboxInv[bUpper][bLower];
    }

    //Thực hiện phép nhân hữu hạn
    private byte[][] invMixColumns(byte state[][]) {
        byte stateNew[][] = new byte[state.length][state[0].length];
        for (int c = 0; c < Nb; c++) {
            stateNew[0][c] = xor4Bytes(finiteMultiplication(state[0][c], 0x0e), finiteMultiplication(state[1][c], 0x0b), finiteMultiplication(state[2][c], 0x0d), finiteMultiplication(state[3][c], 0x09));
            stateNew[1][c] = xor4Bytes(finiteMultiplication(state[0][c], 0x09), finiteMultiplication(state[1][c], 0x0e), finiteMultiplication(state[2][c], 0x0b), finiteMultiplication(state[3][c], 0x0d));
            stateNew[2][c] = xor4Bytes(finiteMultiplication(state[0][c], 0x0d), finiteMultiplication(state[1][c], 0x09), finiteMultiplication(state[2][c], 0x0e), finiteMultiplication(state[3][c], 0x0b));
            stateNew[3][c] = xor4Bytes(finiteMultiplication(state[0][c], 0x0b), finiteMultiplication(state[1][c], 0x0d), finiteMultiplication(state[2][c], 0x09), finiteMultiplication(state[3][c], 0x0e));
        }
        return stateNew;
    }
    
    //Chuyển đổi chuỗi Hex (length chẵn) thành Hex
    public byte[] decodeHexString(String hexString) {
        //Trường hợp chuỗi độ dài lẻ
        if (hexString.length() % 2 == 1) {
            throw new IllegalArgumentException("Mã Hex sai định dạng!");
        }

        byte[] bytes = new byte[hexString.length() / 2];
        for (int i = 0; i < hexString.length(); i += 2) {
            bytes[i / 2] = hexToByte(hexString.substring(i, i + 2));
        }
        return bytes;
    }
    
    //Chuyển đổi chuỗi Hex (length = 2) thành hex
    public byte hexToByte(String hexString) {
        int firstDigit = toDigit(hexString.charAt(0));
        int secondDigit = toDigit(hexString.charAt(1));
        return (byte) ((firstDigit << 4) + secondDigit);
    }
    
    //Chuyển đổi ký tự hex thành hex
    private int toDigit(char hexChar) {
        int digit = Character.digit(hexChar, 16);
        if(digit == -1) {
            throw new IllegalArgumentException("Mã Hex sai định dạng!");
        }
        return digit;
    }
}

