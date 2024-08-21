package entities;

import lombok.Data;

@Data
public class RequestBody {
    private String email;
    private String password;
    private String company_name;
    private String seller_name;
    private String phone_number;
    private String address;
    private String name;
    private String lastName;
    private int groupdId;
    private String stydyFormat;

    private String category_title;
    private String category_description;
    private Boolean flag;




}
