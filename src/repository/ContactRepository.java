package repository;

import entity.Contact;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ContactRepository {
    public static final String SRC_DATA = "src/data/data.dat";

    public static final String SRC_CONTACT = "src/data/contacts.csv";

    public List<Contact> getAll() {
        List<Contact> contacts = new ArrayList<>();
        File file = new File(SRC_DATA);
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            contacts = (List<Contact>) objectInputStream.readObject();
            objectInputStream.close();
        } catch (FileNotFoundException e) {
            System.out.println("Loi ko tim thay file");
        } catch (IOException e) {
            if (e.getMessage() != null) {
                System.out.println(e.getMessage());
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Loi khong tim thay class");
        }
        return contacts;
    }

    public void save(Contact c) {
        List<Contact> contacts = getAll();
        contacts.add(c);
        writeFileBinary(contacts);
    }

    public void writeFileBinary(List<Contact> contacts) {
        File file = new File(SRC_DATA);
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(contacts);
            objectOutputStream.close();
        } catch (FileNotFoundException e) {
            System.out.println("Looix khong tim thay file");
        } catch (IOException e) {
            System.out.println("loi ghi file");
        }
    }

    public void deleteByPhone(String phone) {
        List<Contact> contacts = getAll();
        for (Contact contact : contacts) {
            if (contact.getPhoneNumber().equals(phone)) {
                contacts.remove(contact);
                break;
            }
        }
        writeFileBinary(contacts);
    }

    public Contact findByPhoneNumber(String phone) {
        List<Contact> contacts = getAll();
        for (Contact contact : contacts) {
            if (contact.getPhoneNumber().equals(phone)) {
                return contact;
            }
        }

        return null;
    }

    public List<Contact> searchContact(String search) {
        List<Contact> contacts = getAll();
        List<Contact> res = new ArrayList<>();
        String query = search.toLowerCase();
        for (Contact contact : contacts) {
            if (contact.getPhoneNumber().toLowerCase().contains(query) || contact.getFullName().toLowerCase().contains(query)) {
                res.add(contact);
            }
        }

        return res;
    }

    public void update(String phone, Contact contact) {
        List<Contact> items = getAll();
        Contact old = findByPhoneNumber(phone);
        int index = items.indexOf(old);
        items.set(index, contact);
        writeFileBinary(items);
    }

    public void importFile() {
        File file = new File(SRC_CONTACT);
        List<Contact> contacts = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            Contact contact;
            bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                String[] temp = line.split(",");
                contact = new Contact(temp[0], temp[1], temp[2], temp[3], temp[4], temp[5], temp[6]);
                contacts.add(contact);
            }
            writeFileBinary(contacts);
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Không tìm thấy file");
        } catch (IOException e) {
            System.out.println("Lỗi đọc file");
        }
    }

    public void exportFile() {
        File file = new File(SRC_CONTACT);
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        List<Contact> contacts = getAll();
        try {
            fileWriter = new FileWriter(file);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("Số điện thoại,Nhóm,Họ tên,Giới tính,Địa chỉ,Ngày sinh,Email");
            bufferedWriter.newLine();
            for (Contact temp : contacts) {
                bufferedWriter.write(toString(temp));
                bufferedWriter.newLine();
            }

        } catch (IOException e) {
            System.out.println("Lỗi ghi file");
        } finally {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    System.out.println("Lỗi đóng file");
                }
            }
        }
    }

    private String toString(Contact c) {
        return c.getPhoneNumber() + "," + c.getGroup() + "," + c.getFullName() + "," + c.getGender() + "," + c.getAddress() + "," + c.getEmail() + "," + c.getBirthday();
    }
}
