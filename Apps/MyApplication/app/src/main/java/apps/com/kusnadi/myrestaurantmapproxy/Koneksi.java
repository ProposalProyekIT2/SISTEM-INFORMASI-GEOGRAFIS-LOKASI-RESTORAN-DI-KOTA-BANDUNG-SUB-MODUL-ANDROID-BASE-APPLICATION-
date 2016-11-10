package apps.com.kusnadi.myrestaurantmapproxy;

/**
 * Created by Kusnadi on 11/5/2016.
 */

public class Koneksi {
    //URL letak file new_login.php di server menggunakan alamat IP Komputer
    public static final String LOGIN_URL = "http://mytrips.16mb.com/users/login.php";
    // Variabel untuk definisikan Username dan password methode POST sesuai dengan yang di : login.php
    public static final String KEY_USERS = "username";
    public static final String KEY_PASSWORD = "password";
    // Jika respon server adalah sukses login
    public static final String LOGIN_SUCCESS = "success";
    //Kunci untuk Sharedpreferences
    public static final String SHARED_PREF_NAME = "mytripapp";
    //Ini digunakan untuk store username jika User telah Login
    public static final String EMAIL_SHARED_PREF = "username";
    // Ini digunakan untuk store sharedpreference untuk cek user melakukan login atau tidak
    public static final String LOGGEDIN_SHARED_PREF = "loggedin";

    public static final String URL_ADD = "http://mytrips.16mb.com/users/addUsers.php";
    // Filed yang digunakan untuk dikirimkan ke Database, sesuaikan saja dengan Field di Tabel Users
    public static final String KEY_USER_ID = "id";
    public static final String KEY_USER_USERNAME = "username";
    public static final String KEY_USER_PASSWORD = "password";
    public static final String KEY_USER_NAMA = "nama";

    // Tags Format JSON
    public static final String TAG_JSON_ARRAY="result";
    public static final String TAG_ID_USER = "id";
    public static final String TAG_USERNAME_USER = "username";
    public static final String TAG_PASSWORD_USER = "password";
    public static final String TAG_NAMA_USER = "nama";
}
