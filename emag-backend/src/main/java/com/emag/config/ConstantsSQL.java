package com.emag.config;

public class ConstantsSQL {
    public static final String FIND_USER_BY_ID = "select * from users where id=:id";
    public static final String FIND_USER_BY_EMAIL = "select * from users where email=:email";
    public static final String ADD_USER = "insert into users(name,email,password) values(:name,:email,sha1(:password))";
    public static final String SELECT_USER_BY_EMAIL_AND_PASS = "select * from users where email=:email and password = sha1(:password);";
    public static final String GET_ALL_CATEGORIES = "select main.id as id,main.name as main_name,middle.id as middle_id,middle.name as middle_name from main_type as main join middle_type as middle on main.id = middle.main_type_id";
    public static final String INSERT_INTO_ADDRESSES = "insert into addresses(receiver_name,receiver_phone,city,street,floor) values (:receiverName,:receiverPhone,:city,:street,:floor); ";
    public static final String INSERT_INTO_USER_ADDRESSES = "insert into users_addresses(address_id,user_id) values ((SELECT LAST_INSERT_ID() from addresses group by last_insert_id()),:userId); ";
    public static final String GET_ALL_ADDRESSES  = "select a.id as address_id,a.receiver_name,a.receiver_phone,a.city,a.street,a.floor from users u join users_addresses ua on u.id=ua.user_id join addresses a on a.id=ua.address_id  where u.id=:userId";
    public static final String UPDATE_ADDRESS = "update addresses set receiver_name=:receiver_name,receiver_phone=:receiver_phone,city=:city,street=:street,floor=:floor where id=:id";
    public static final String GET_ADDRESS_BY_ID = "select * from addresses a where a.id=:addressId";
    public static final String DELETE_ADDRESS = "delete from addresses where id=:addressId";
    public static final String UPDATE_USER_PERSONAL_INFO = "update users set name=:name,email=:email,phone=:phone,gender=:gender where id=:id";
    public static final String UPDATE_USER_PERSONAL_PICTURE = "update users set profile_url=:profile_url where id=:userId";
    public static final String UPDATE_USER_SUBSCRIBE_STATUS = "update users set isSubscribed=1 where email=:email";
    public static final String GET_PRODUCT_BY_ID = "select p.id as product_id,p.name,p.picture_url,p.price,p.quantity,p.discount,p.description,c.id as  comment_id,c.user_id,c.value,u.name as uname,c.stars from products p left join comments c on p.id = c.product_id  left join users u on c.user_id=u.id where p.id=:id order by c.id ;";
    public static final String GET_ALL_PRODUCTS = "select * from products";
    public static final String GET_ALL_PRODUCTS_BY_INNER_CATEGORY_ID = "select * from products where middle_type_id=:id";
    public static final String DELETE_PRODUCT_BY_ID = "delete from products where id=:productId";
    public static final String UPDATE_PRODUCT_BY_ID = "update products set name=:name,middle_type_id=:categoryId,price=:price,quantity=:quantity,description=:description, discount=:discount where id =:id ";
    public static final String ORDER_PRODUCTS_BY_PRICE = "select * from products order by price";
    public static final String GET_PRODUCTS_BY_ID_INTERVAL =  "select * from products where id in (:ids)";
    public static final String INSERT_INTO_PRODUCTS = "insert into products(name,picture_url,price,middle_type_id,quantity,description,discount) values (:name,:picture_url,:price,:middle_type_id,:quantity,:description,:discount)";
    public static final String GET_PRODUCTS_FILTERED_BY_NAME = "select * from products where name LIKE '%:searchInput%'";
}
