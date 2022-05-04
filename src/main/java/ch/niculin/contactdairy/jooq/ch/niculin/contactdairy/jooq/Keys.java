/*
 * This file is generated by jOOQ.
 */
package ch.niculin.contactdairy.jooq;


import ch.niculin.contactdairy.jooq.tables.Datum;
import ch.niculin.contactdairy.jooq.tables.Person;
import ch.niculin.contactdairy.jooq.tables.records.DatumRecord;
import ch.niculin.contactdairy.jooq.tables.records.PersonRecord;

import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables in the
 * default schema.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<DatumRecord> DATUM__ = Internal.createUniqueKey(Datum.DATUM, DSL.name(""), new TableField[] { Datum.DATUM.ID, Datum.DATUM.DATUM_ }, true);
    public static final UniqueKey<PersonRecord> PERSON__ = Internal.createUniqueKey(Person.PERSON, DSL.name(""), new TableField[] { Person.PERSON.ID, Person.PERSON.NAME, Person.PERSON.SURNAME }, true);
}