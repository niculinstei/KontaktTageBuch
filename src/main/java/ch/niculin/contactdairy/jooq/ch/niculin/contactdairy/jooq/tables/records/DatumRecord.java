/*
 * This file is generated by jOOQ.
 */
package ch.niculin.contactdairy.jooq.tables.records;


import ch.niculin.contactdairy.jooq.tables.Datum;

import java.time.LocalDate;

import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DatumRecord extends UpdatableRecordImpl<DatumRecord> implements Record2<Integer, LocalDate> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>Datum.ID</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>Datum.ID</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>Datum.datum</code>.
     */
    public void setDatum(LocalDate value) {
        set(1, value);
    }

    /**
     * Getter for <code>Datum.datum</code>.
     */
    public LocalDate getDatum() {
        return (LocalDate) get(1);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record2<Integer, LocalDate> key() {
        return (Record2) super.key();
    }

    // -------------------------------------------------------------------------
    // Record2 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row2<Integer, LocalDate> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    @Override
    public Row2<Integer, LocalDate> valuesRow() {
        return (Row2) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return Datum.DATUM.ID;
    }

    @Override
    public Field<LocalDate> field2() {
        return Datum.DATUM.DATUM_;
    }

    @Override
    public Integer component1() {
        return getId();
    }

    @Override
    public LocalDate component2() {
        return getDatum();
    }

    @Override
    public Integer value1() {
        return getId();
    }

    @Override
    public LocalDate value2() {
        return getDatum();
    }

    @Override
    public DatumRecord value1(Integer value) {
        setId(value);
        return this;
    }

    @Override
    public DatumRecord value2(LocalDate value) {
        setDatum(value);
        return this;
    }

    @Override
    public DatumRecord values(Integer value1, LocalDate value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached DatumRecord
     */
    public DatumRecord() {
        super(Datum.DATUM);
    }

    /**
     * Create a detached, initialised DatumRecord
     */
    public DatumRecord(Integer id, LocalDate datum) {
        super(Datum.DATUM);

        setId(id);
        setDatum(datum);
    }
}
