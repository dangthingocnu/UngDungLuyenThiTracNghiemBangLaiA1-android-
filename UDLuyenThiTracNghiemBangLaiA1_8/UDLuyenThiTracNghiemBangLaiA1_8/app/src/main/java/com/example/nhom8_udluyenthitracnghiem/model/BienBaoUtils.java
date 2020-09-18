package com.example.nhom8_udluyenthitracnghiem.model;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.nhom8_udluyenthitracnghiem.model.BienBao;
import com.example.nhom8_udluyenthitracnghiem.model.LoaiBienBao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class BienBaoUtils {
    Context context;
    static ArrayList<BienBao> furnitureHistory = new ArrayList<>();

    public BienBaoUtils(Context context) {
        this.context = context;
    }

    static String filename = "database";
    static ArrayList<BienBao> furnituresHistory = new ArrayList<>();

    public BienBaoUtils() {

    }
    public ArrayList<BienBao> getFurnituresHistory() {
        return furnituresHistory;
    }

    public void addFurnitureHistorry(BienBao furniture) {
        if (furnitureHistory.indexOf(furniture) > 0) {
            this.furnitureHistory.add(0, furniture);
        }
    }

    public ArrayList<BienBao> getFurnitureHistory() {
        return this.furnitureHistory;
    }

    public void WriteToFileInternal(ArrayList<BienBao> arrayList) {
        try {
            File file = new File(context.getFilesDir(), filename);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(arrayList);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<BienBao> LoadFileInternal() {
        ArrayList<BienBao> arrayList = new ArrayList<>();
        try {
            File file = new File(context.getFilesDir(), filename);
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            arrayList = (ArrayList<BienBao>)
                    objectInputStream.readObject();
            Log.d("FunitureApp", arrayList.size() + "");
            return arrayList;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<BienBao> getMockData() {
        ArrayList<BienBao> tmp = new ArrayList<>();
        tmp.add(new BienBao("Sealy 1",
                "Modern wooden table comes with easy to pull out fabric drawer and sleek plastic handle.", "hinh_1.png"));
        tmp.add(new BienBao("GreenForest L-Shape Corner 1",
                "L-Shaped gaming desk is made out of a beautiful MDF that appearance and functionality are classy and eficient.", "hinh_2.png"));
        tmp.add(new BienBao("GreenForest L-Shape Corner 2",
                "Modern wooden table comes with easy to pull out fabric drawer and sleek plastic handle.", "hinh_3.png"));
        tmp.add(new BienBao("Zinus Jocenlyn Contemporary 65 Inch 1",
                "Polyester Easily asembles with a friend, no tools needed, in under 20 minutes Modern armless design with stress-free fabrics." , "hinh_4.png"));
        tmp.add(new BienBao("Sealy 1",
                "Modern wooden table comes with easy to pull out fabric drawer and sleek plastic handle."  , "hinh_5.png"));
        tmp.add(new BienBao("Sealy 3 ",
                "Modern wooden table comes with easy to pull out fabric drawer and sleek plastic handle." , "hinh_4.png"));
        tmp.add(new BienBao("Zinus Jocenlyn Contemporary 65 Inch 2",
                "Modern wooden table comes with easy to pull out fabric drawer and sleek plastic handle." , "hinh_1.png"));

        return tmp;
    }


    public Bitmap convertStringToBitmapFromAccess(String filename) {
        AssetManager assetManager = context.getAssets();
        try {
            InputStream is = assetManager.open(filename);
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<LoaiBienBao> getMockDataCategories(){
        ArrayList<LoaiBienBao> tmp = new ArrayList<>();
        tmp.add(new LoaiBienBao("BIỂN BÁO NGUY HIỂM VÀ CẢNH BÁO"," Có dạng hình tam giác đều, viền đỏ, nền màu vàng, hình vẽ màu đenTác dụng:\n" +
                " - Cảnh báo các tình huống nguy hiểm có thể xảy ra cho người tham gia giao thông, khi gặp biển báo nguy hiểm người lái xe phải giảm ngay tốc độ.", ""));
        tmp.add(new LoaiBienBao("BIỂN BÁO CẤM","Có dạng hình tròn, viền đỏ, nền màu trắng, hình vẽ màu đenTác dụng:\n" +
                " - Biểu thị các điều cấm, người tham gia giao thông phải chấp hành những điều cấm mà biển đã báo hiệu.", ""));
        tmp.add(new LoaiBienBao("BIỂN BÁO HIỆU LỆNH"," Có dạng hình tròn, nền màu xanh, hình vẽ màu trắngTác dụng:\n" +
                " - Báo hiệu lệnh cho người tham gia giao thông phải chấp hành.", ""));
        tmp.add(new LoaiBienBao("BIỂN BÁO CHỈ DẪN"," Có dạng hình vuông hoặc hình chữ nhật, nền màu xanh, hình vẽ màu trắngTác dụng:\n"+
                " - Chỉ dẫn hướng đi hoặc những điều cần biết có ích khác cho người tham gia giao thông.", ""));
        tmp.add(new LoaiBienBao("BIỂN BÁO PHỤ"," Có dạng hình vuông hoặc hình chữ nhật, viền đen, nền trắng, hình vẽ màu đenTác dụng:\n" +
                " - Được đặt kết hợp với các biển báo nguy hiểm, biển báo cấm, biển hiệu lệnh và chỉ dẫn nhằm thuyết minh, bổ sung, hiểu rõ các biển đó.",  ""));
        return tmp;
    }

    public ArrayList<BienBao> getFurnitureFromCategories(int pos) {
        ArrayList<BienBao> tmp = new ArrayList<>();
        switch (pos) {
            case 0:
                tmp.add(new BienBao("Biển số 208 \"Giao nhau với đường ưu tiên\"",
                        " Để báo trước sắp đến nơi giao nhau với đường ưu tiên\n" +
                                "\n" +
                                "Chú ý: biển này tương tự, chỉ quay lộn ngược đầu so với biển báo nguy hiểm số 247.", "hinh1.png"));
                tmp.add(new BienBao("Biển số 209 \"Giao nhau có tín hiệu đèn\"",
                        " báo trước nơi giao nhau có điều khiển giao thông bằng tín hiệu đèn trong trường hợp người lái xe khó quan sát để kịp thời xử lý.",  "hinh2.png"));
                tmp.add(new BienBao("Biển số 210 \" Giao nhau với đường sắt có rào chắn\"",
                        " báo trước sắp đến chỗ giao nhau giữa đường bộ và đường sắt có rào chắn kín hay rào chắn nửa kín và có nhân viên ngành đường sắt điều khiển giao thông.", "hinh3.png"));
                tmp.add(new BienBao("Biển số 211a \"Giao nhau với đường sắt không có rào chắn\"",
                        "báo trước sắp đến chỗ giao nhau giữa đường bộ và đường sắt không có rào chắn, không có người điều khiển giao thông.", "hinh4.png"));
                tmp.add(new BienBao("Biển số 204 \"Đường hai chiều\"",
                        " báo trước sắp đến đoạn đường do sửa chữa hoặc có trở ngại ở một phía đường mà phải tổ chức đi lại cho phương tiện cả hai chiều trên phía đường còn lại hoặc để báo trước đoạn đường đôi tạm thời hoặc đoạn đường có chiều xe đi và về đi chung.", "hinh5.png"));
                tmp.add(new BienBao("Biển số 205a \"Đường giao nhau cùng cấp\"",
                        " báo trước sắp đến nơi giao nhau cùng mức của các tuyến đường cùng cấp (không có đường nào ưu tiên)  trên cùng một mặt bằng.", "hinh6.png"));
                tmp.add(new BienBao("Biển số 207a \"Giao nhau với đường không ưu tiên\"",
                        "báo trước sắp đến nơi giao nhau với đường không ưu tiên.", "hinh7.png"));
                tmp.add(new BienBao("Biển số 244 \"Đoạn đường hay xảy ra tai nạn\"",
                        "cảnh báo nguy hiểm đoạn đường phía trước thường xảy ra tai nạn để lái xe cần đặc biệt chú ý.", "hinh8.png"));
                break;
            case 1:
                tmp.add(new BienBao("Biển số 101 \"Đường cấm\"",
                        "báo đường cấm tất cả các loại phương tiện (cơ giới và thô sơ) đi lại cả hai hướng, trừ các xe được ưu tiên theo quy định.", "hinh10.png"));
                tmp.add(new BienBao("Biển số 122 \"Dừng lại\"",
                        " buộc các loại xe cơ giới và thô sơ kể cả xe được ưu tiên theo quy định dừng lại trước biển hoặc trước vạch ngang đường và chỉ được phép đi khi thấy các tín hiệu (do người điều khiển giao thông hoặc đèn cờ) cho phép đi. Trong trường hợp trên đường không đặt tín hiệu đèn cờ, không có người  điều khiển giao thông hoặc các tín hiệu đèn không bật sáng thì người lái xe chỉ được phép đi khi trên đường không còn nguy cơ mất an toàn giao thông.", "hinh11.png"));
                tmp.add(new BienBao("Biển số 102 \"Cấm đi ngược chiều\"",
                        "báo đường cấm tất cả các loại xe (cơ giới và thô sơ) đi vào theo chiều đặt biển, trừ các xe được ưu tiên theo quy định.","hinh12.png"));
                tmp.add(new BienBao("Biển số 104 \"Cấm mô tô\"",
                        " báo đường cấm tất cả các loại môtô đi qua, trừ các xe môtô được ưu tiên theo quy định.", "hinh13.png"));
                tmp.add(new BienBao("Biển số 105 \"Cấm ô tô và mô tô\"",
                        " báo đường cấm tất cả các loại xe cơ giới và môtô đi qua trừ xe gắn máy và các xe được ưu tiên theo quy định.","hinh14.png"));
                tmp.add(new BienBao("\tBiển số 123a \"Cấm rẽ trái\"",
                        "cấm các loại xe (cơ giới và thô sơ) rẽ sang phía trái ở những vị trí đường giao nhau trừ các xe được ưu tiên theo quy định. Biển này cũng cấm xe quay đầu. ", "hinh15.png"));
                tmp.add(new BienBao("Biển số 124a \"Cấm quay xe\"",
                        " báo cấm các loại xe cơ giới và thô sơ quay đầu (theo kiểu chữ U) trừ các xe được ưu tiên theo quy định. Biển này không cấm rẽ trái.","hinh16.png"));
                tmp.add(new BienBao("Biển số 130 \"Cấm dừng xe và đỗ xe\"",
                        " báo nơi cấm dừng xe và đỗ xe. Biển có hiệu lực cấm các loại xe cơ giới dừng và đỗ ở phía đường có đặt biển trừ các xe được ưu tiên theo quy định.", "hinh17.png"));

                break;
            case 2:
                tmp.add(new BienBao("Biển số 303 \"Nơi giao nhau chạy theo vòng xuyến\"",
                        "báo cho các loại xe (thô sơ và cơ giới) phải chạy vòng theo đảo an toàn ở nơi đường giao nhau.", "hinh18.png"));
                tmp.add(new BienBao("Biển số 301a \" Hướng đi phải theo \"",
                        "các xe chỉ được đi thẳng (trừ xe được quyền ưu tiên theo quy định).", "hinh19.png"));
                tmp.add(new BienBao("Biển số 301i \" Hướng đi phải theo \"",
                        "các xe chỉ được rẽ phải và rẽ trái (trừ xe được quyền ưu tiên theo quy định).", "hinh20.png"));
                tmp.add(new BienBao("Biển số 301e \" Hướng đi phải theo \"",
                        "các xe chỉ được rẽ trái (trừ xe được quyền ưu tiên theo quy định).  Biển này được đặt ở trước nơi đường giao nhau, có tác dụng tại nơi giao nhau đằng sau mặt biển.", "hinh21.png"));
                tmp.add(new BienBao("Biển số 305 \"Đường dành cho người đi bộ\"",
                        "báo đường dành cho người đi bộ. Các loại xe cơ giới và thô sơ kể cả các xe được ưu tiên theo quy định không được phép đi vào đường đã đặt biển này, trừ trường hợp đi cắt ngang qua nhưng phải đảm bảo tuyệt đối an toàn cho người đi bộ.", "hinh22.png"));
                tmp.add(new BienBao("Biển số 304 \" Đường dành cho xe thô  sơ\"",
                        "báo đường dành cho xe thô sơ (kể cả xe của người tàn tật) và người đi bộ. Biển bắt buộc các  loại xe thô sơ (kể cả xe của người tàn tật) và người đi bộ phải dùng đường dành riêng này để đi và cấm các xe cơ giới kể cả xe gắn máy, các xe được ưu tiên theo quy định đi vào đường đã đặt biển này.", "hinh23.png"));
                break;
            case 3:
                tmp.add(new BienBao("Biển số 401 \"Bắt đầu đường ưu tiên\"",
                        "Để biểu thị ưu tiên cho các phương tiện trên đường có đặt biển này được đi trước. Biển đặt tại vị trí thích hợp trước khi đường nhánh sắp nhập vào trục đường chính, yêu cầu phương tiện từ đường nhánh ra phải dừng lại nhường cho phương tiện trên đường chính đi trước.", "hinh24.png"));
                tmp.add(new BienBao("Biển số 402 \"Hết đoạn đường ưu tiên\"",
                        "Báo hiệu hết đoạn đường được ưu tiên.", "hinh25.png"));
                tmp.add(new BienBao("Biển số 406 \"Được ưu tiên qua đường hẹp\" ",
                        "Để chỉ dẫn cho người lái xe cơ giới biết mình được quyền ưu tiên đi trước trên đoạn đường hẹp. Nếu trên hướng đi ngược chiều có xe (cơ giới hoặc thô sơ) đã đi vào phạm vi đường hẹp thì xe đi theo chiều ưu tiên cũng phải nhường đường.", "hinh26.png"));
                tmp.add(new BienBao("Biển số 447 \"Biển báo cầu vượt liên thông\"",
                        "Biển đặt tại vị trí trước khi vào cầu vượt có tổ chức giao thông liên thông giữa các tuyến. ", "hinh27.png"));
                tmp.add(new BienBao("Tuyến đường đối ngoại",
                        "", "hinh28.png"));
                break;
//            case 4:
//                tmp.add(new Furniture("Biển số 401 \"Bắt đầu đường ưu tiên\"",
//                        "Để h đi trước.",
//                        "hinh24.png"));
//                tmp.add(new Furniture("Biển số 402 \"Hết đoạn đường ưu tiên\"",
//                        "Báo hiệu hết đoạn đường được ưu tiên.",
//                        "hinh25.png"));
//                tmp.add(new Furniture("Biển số 406 \"Được ưu tiên qua đường hẹp\" ",
//                        "Để chỉ dẫn cho người lái xe cơ giới biết mình đưào phạm vi đường hẹp thì xe đi theo chiều ưu tiên cũng phải nhường đường.",
//                        "hinh26.png"));
//                tmp.add(new Furniture("Biển số 447 \"Biển báo cầu vượt liên thông\"",
//                        "Biển đặt tại vị trí trước khi vào cầu vượt có tổ chức giao thông liên thông giữa các tuyến. ",
//                        "hinh27.png"));
//                tmp.add(new Furniture("Tuyến đường đối ngoại",
//                        "", "hinh28.png"));
//                break;
        }
        return tmp;
    }

}
