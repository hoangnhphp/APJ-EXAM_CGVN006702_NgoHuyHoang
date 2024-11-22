package view;

import entity.Contact;
import repository.ContactRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainView {
    private static Scanner scanner = new Scanner(System.in);
    private static ContactRepository contactRepository = new ContactRepository();
    public static void main(String[] args) {
        List<Contact> contacts;
        while (true) {
            System.out.println("--------- CHƯƠNG TRÌNH QUẢN LÝ DANH BẠ ------------");
            System.out.println("1. Xem danh sách");
            System.out.println("2. Thêm mới");
            System.out.println("3. Cập nhật");
            System.out.println("4. Xóa");
            System.out.println("5. Tìm kiếm");
            System.out.println("6. Đọc từ file");
            System.out.println("7. Ghi vào file");
            System.out.println("8. Thoát");
            System.out.print("Chọn chức năng: ");
            int choice = inputChoice();
            switch (choice) {
                case 1:
                    System.out.println("Hiển thị thông tin liên lạc");
                    contacts = contactRepository.getAll();
                    display(contacts);
                    break;
                case 2:
                    System.out.println("Thêm mới danh bạ");
                    add();
                    break;
                case 3:
                    System.out.println("Thay đổi thông tin danh bạ");
                    edit();
                    break;
                case 4:
                    delete();
                    break;
                case 5:
                    search();
                    break;
                case 6:
                    importFile();
                    break;
                case 7:
                    exportFile();
                    break;
                case 8:
                    return;
            }
        }
    }

    private static void search() {
        System.out.println("Nhập số điện thoại hoặc họ tên");
        String search = scanner.nextLine();

        List<Contact> contacts = contactRepository.searchContact(search);
        display(contacts);
    }

    private static void display(List<Contact> contacts) {
        if (contacts.isEmpty()) {
            System.out.println("Không có dữ liệu!!!");
        } else {
            for (Contact contact : contacts) {
                System.out.println(contact);
            }
        }
    }

    private static int inputChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Nhập sai lựa chọn. Mời bạn nhập lại");
        } catch (Exception e) {
            System.out.println("Lỗi khác");
        }
        return 0;
    }

    private static void add() {
        Contact contact = inputContact(null);
        contactRepository.save(contact);
        System.out.println("Thêm mới thông tin liên lạc thành công");
    }

    private static void edit() {
        System.out.println("Vui lòng nhập số điện thoại muốn sửa thông tin:");
        String phone = scanner.nextLine();
        if (phone.isEmpty()) {
            return;
        }
        Contact contact = contactRepository.findByPhoneNumber(phone);
        if (contact == null) {
            System.out.println("Không tìm được danh bạ với số điện thoại trên.");
            edit();
        } else {
            Contact c = inputContact(contact.getPhoneNumber());
            contactRepository.update(contact.getPhoneNumber(), c);
            System.out.println(c);
            System.out.println("Thay đổi thông tin liên lạc thành công");
        }
    }

    public static Contact inputContact(String phone) {
        if (phone == null) {
            System.out.println("Nhập số điện thoại của bạn");
            phone = scanner.nextLine();
            while (phone.isEmpty() || !phone.matches("\\d{10}")) {
                System.out.println("Số điện thoại không hợp lệ. Số điện thoại phải gồm 10 số");
                System.out.println("Nhập lại số điện thoại của bạn");
                phone = scanner.nextLine();
            }
            while (!isValidPhone(phone)) {
                System.out.println("Số điện thoại đã tồn tại");
                System.out.println("Nhập lại số điện thoại của bạn");
                phone = scanner.nextLine();
            }
        }

        System.out.println("Nhập nhóm của danh bạ");
        String group = scanner.nextLine();
        while (group.isEmpty()) {
            System.out.println("Tên nhóm không được để rỗng");
            System.out.println("Nhập lại tên nhóm của bạn");
            group = scanner.nextLine();
        }

        System.out.println("Nhập họ và tên");
        String fullName = scanner.nextLine();
        while (fullName.isEmpty()) {
            System.out.println("Họ và tên không được để rỗng");
            System.out.println("Nhập lại họ tên của bạn");
            fullName = scanner.nextLine();
        }
        System.out.println("Nhập giới tính");
        String gender = scanner.nextLine();
        while (gender.isEmpty() || !gender.matches("(nam|nữ)")) {
            System.out.println("Giới tính không hợp lệ. Giới tính chỉ được điền là nam hoặc nữ");
            System.out.println("Nhập lại giới tính của bạn");
            gender = scanner.nextLine();
        }

        System.out.println("Nhập địa chỉ");
        String address = scanner.nextLine();
        while (address.isEmpty()) {
            System.out.println("Địa chỉ không được để rỗng");
            System.out.println("Nhập lại địa chỉ của bạn");
            address = scanner.nextLine();
        }

        System.out.println("Nhập ngày sinh (định dạng dd/MM/yyyy)");
        String birthday = scanner.nextLine();
        while (birthday.isEmpty() || !isValidDate(birthday)) {
            System.out.println("Ngày sinh không được để rỗng và phải đúng định dạng dd/MM/yyyy");
            System.out.println("Nhập lại ngày sinh của bạn");
            birthday = scanner.nextLine();
        }

        System.out.println("Nhập email");
        String email = scanner.nextLine();
        while (email.isEmpty() || !isValidEmail(email)) {
            System.out.println("Email không được phép để rỗng và phải đúng định dạng chuẩn quốc tế");
            System.out.println("Nhập lại email của bạn");
            email = scanner.nextLine();
        }
        return new Contact(phone, group, fullName, gender, address, email, birthday);
    }

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isValidDate(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);  // Tắt chế độ "lỏng lẻo" của SimpleDateFormat
        try {
            Date date = sdf.parse(dateStr);  // Thử parse ngày
            return date != null;
        } catch (ParseException e) {
            return false;  // Ngày không hợp lệ
        }
    }

    public static boolean isValidPhone(String phone) {
        Contact contact = contactRepository.findByPhoneNumber(phone);
        return contact == null;
    }

    public static void delete() {
        System.out.print("Nhập số điện thoại cần xóa: ");
        String phoneNum = scanner.nextLine();
        if (phoneNum.isEmpty()) {
            return;
        }
        Contact contact = contactRepository.findByPhoneNumber(phoneNum);
        if (contact == null) {
            System.out.println("Không tìm được danh bạ với số điện thoại trên.");
            delete();
        } else {
            System.out.println("Bạn có chắc muốn xóa hay không. Nhấn Y nếu đồng ý hoặc bất kỳ kí tự khác nếu không?");
            String isConfirm = scanner.nextLine();
            if (isConfirm.equalsIgnoreCase("y")) {
                contactRepository.deleteByPhone(phoneNum);
                System.out.println("Xóa thông tin liên lạc thành công");
            }
        }
    }

    public static void importFile() {
        System.out.println("Bạn có chắc muốn thêm dữ liệu từ file contacts.csv hay không. Lưu ý dữ liệu cũ sẽ hoàn toàn biến mất!!!");
        System.out.println("Nhấn Y nếu đồng ý hoặc bất kỳ kí tự khác nếu không?");
        String isConfirm = scanner.nextLine();
        if (isConfirm.equalsIgnoreCase("y")) {
            contactRepository.importFile();
            System.out.println("Đã hoàn thành import dữ liệu từ file contacts.csv");
        }
    }

    public static void exportFile() {
        System.out.println("Bạn có chắc muốn xuất dữ liệu ra file contacts.csv hay không. Lưu ý dữ liệu cũ sẽ hoàn toàn biến mất!!!");
        System.out.println("Nhấn Y nếu đồng ý hoặc bất kỳ kí tự khác nếu không?");
        String isConfirm = scanner.nextLine();
        if (isConfirm.equalsIgnoreCase("y")) {
            contactRepository.exportFile();
            System.out.println("Đã hoàn thành xuất dữ liệu ra file contacts.csv");
        }
    }
}
