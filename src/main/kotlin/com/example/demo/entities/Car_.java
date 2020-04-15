package com.example.demo.entities;

import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Car.class)
public abstract class Car_ {

	public static volatile SingularAttribute<Car, BodyType> bodyType;
	public static volatile SingularAttribute<Car, Integer> createUserId;
	public static volatile SingularAttribute<Car, Integer> updateUserId;
	public static volatile SingularAttribute<Car, Long> price;
	public static volatile SingularAttribute<Car, String> name;
	public static volatile ListAttribute<Car, Part> parts;
	public static volatile SingularAttribute<Car, LocalDateTime> createdDateTime;
	public static volatile SingularAttribute<Car, User> createUser;
	public static volatile SingularAttribute<Car, Integer> id;
	public static volatile SingularAttribute<Car, LocalDateTime> updatedDateTime;

	public static final String BODY_TYPE = "bodyType";
	public static final String CREATE_USER_ID = "createUserId";
	public static final String UPDATE_USER_ID = "updateUserId";
	public static final String PRICE = "price";
	public static final String NAME = "name";
	public static final String PARTS = "parts";
	public static final String CREATED_DATE_TIME = "createdDateTime";
	public static final String CREATE_USER = "createUser";
	public static final String ID = "id";
	public static final String UPDATED_DATE_TIME = "updatedDateTime";

}

