package com.xml;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2010-6-16
 * Time: 22:53:01
 * To change this template use File | Settings | File Templates.
 */
import java.sql.Timestamp;

public class formfields {
        private int id;
        private String bookname;
        private String username;
        private String telephone;
        private String postcode;
        private String address;
        private Timestamp createdate;

        public formfields() {
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getBookname() {
            return bookname;
        }

        public void setBookname(String bname) {
            this.bookname = bname;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String uname) {
            this.username = uname;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public Timestamp getCreatedate() {
            return createdate;
        }

        public void setCreatedate(Timestamp createdate) {
            this.createdate = createdate;
        }

        public String getPostcode() {
            return postcode;
        }

        public void setPostcode(String postcode) {
            this.postcode = postcode;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
}
