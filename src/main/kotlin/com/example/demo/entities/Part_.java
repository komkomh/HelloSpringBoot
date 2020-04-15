package com.example.demo.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Part.class)
public abstract class Part_ {

	public static volatile SingularAttribute<Part, Car> car;
	public static volatile SingularAttribute<Part, Long> price;
	public static volatile SingularAttribute<Part, String> name;
	public static volatile SingularAttribute<Part, Integer> id;
	public static volatile SingularAttribute<Part, PartType> partType;

	public static final String CAR = "car";
	public static final String PRICE = "price";
	public static final String NAME = "name";
	public static final String ID = "id";
	public static final String PART_TYPE = "partType";

}

