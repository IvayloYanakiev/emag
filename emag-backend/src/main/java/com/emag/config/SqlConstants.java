package com.emag.config;

public class SqlConstants {
    public static final String FIND_USER_BY_ID = "select * from users where id=:id";
    public static final String FIND_USER_BY_EMAIL = "select * from users where email=:email";
    public static final String ADD_USER = "insert into users(name,email,password) values(:name,:email,sha1(:password))";
    public static final String SELECT_USER_BY_EMAIL_AND_PASS = "select * from users where email=:email and password = sha1(:password);";
    public static final String GET_ALL_CATEGORIES = "select main.id as id,main.name as main_name,middle.id as middle_id,middle.name as middle_name from main_type as main join middle_type as middle on main.id = middle.main_type_id";
    public static final String INSERT_INTO_ADDRESSES = "insert into addresses(receiver_name,receiver_phone,city_id,street,floor) values (:receiverName,:receiverPhone,:cityId,:street,:floor); ";
    public static final String INSERT_INTO_USER_ADDRESSES = "insert into users_addresses(address_id,user_id) values ((SELECT LAST_INSERT_ID() from addresses group by last_insert_id()),:userId); ";
    public static final String GET_ALL_ADDRESSES  = "select a.id as address_id,a.receiver_name,a.receiver_phone,c.id as city_id,c.name as city,a.street,a.floor from users u join users_addresses ua on u.id=ua.user_id join addresses a on a.id=ua.address_id join cities c on a.city_id=c.id where u.id=:userId;";
    public static final String UPDATE_ADDRESS = "update addresses set receiver_name=:receiver_name,receiver_phone=:receiver_phone,city_id=:city_id,street=:street,floor=:floor where id=:id";
    public static final String GET_ADDRESS_BY_ID = "select a.id as address_id,a.receiver_name,a.receiver_phone,c.id as city_id,a.street,a.floor,c.name as city_name from addresses a join cities c on a.city_id=c.id where a.id=:addressId";
    public static final String DELETE_ADDRESS = "delete from addresses where id=:addressId";
    public static final String UPDATE_USER_PERSONAL_INFO = "update users set name=:name,email=:email,phone=:phone,gender=:gender where id=:id";
    public static final String UPDATE_USER_PERSONAL_PICTURE = "update users set profile_url=:profile_url where id=:userId";

}
