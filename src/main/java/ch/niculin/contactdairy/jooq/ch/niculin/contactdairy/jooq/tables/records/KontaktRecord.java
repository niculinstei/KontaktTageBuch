/*
 * This file is generated by jOOQ.
 */
package ch.niculin.contactdairy.jooq.tables.records;


import ch.niculin.contactdairy.jooq.tables.Kontakt;

import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.TableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class KontaktRecord extends TableRecordImpl<KontaktRecord> implements Record2<Integer, Integer> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>Kontakt.datumID</code>.
     */
    public void setDatumid(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>Kontakt.datumID</code>.
     */
    public Integer getDatumid() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>Kontakt.personID</code>.
     */
    public void setPersonid(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>Kontakt.personID</code>.
     */
    public Integer getPersonid() {
        return (Integer) get(1);
    }

    // -------------------------------------------------------------------------
    // Record2 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row2<Integer, Integer> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    @Override
    public Row2<Integer, Integer> valuesRow() {
        return (Row2) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return Kontakt.KONTAKT.DATUMID;
    }

    @Override
    public Field<Integer> field2() {
        return Kontakt.KONTAKT.PERSONID;
    }

    @Override
    public Integer component1() {
        return getDatumid();
    }

    @Override
    public Integer component2() {
        return getPersonid();
    }

    @Override
    public Integer value1() {
        return getDatumid();
    }

    @Override
    public Integer value2() {
        return getPersonid();
    }

    @Override
    public KontaktRecord value1(Integer value) {
        setDatumid(value);
        return this;
    }

    @Override
    public KontaktRecord value2(Integer value) {
        setPersonid(value);
        return this;
    }

    @Override
    public KontaktRecord values(Integer value1, Integer value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached KontaktRecord
     */
    public KontaktRecord() {
        super(Kontakt.KONTAKT);
    }

    /**
     * Create a detached, initialised KontaktRecord
     */
    public KontaktRecord(Integer datumid, Integer personid) {
        super(Kontakt.KONTAKT);

        setDatumid(datumid);
        setPersonid(personid);
    }
}