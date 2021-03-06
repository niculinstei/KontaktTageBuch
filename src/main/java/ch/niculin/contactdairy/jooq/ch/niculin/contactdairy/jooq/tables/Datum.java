/*
 * This file is generated by jOOQ.
 */
package ch.niculin.contactdairy.jooq.tables;


import ch.niculin.contactdairy.jooq.DefaultSchema;
import ch.niculin.contactdairy.jooq.Keys;
import ch.niculin.contactdairy.jooq.tables.records.DatumRecord;

import java.time.LocalDate;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row2;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Datum extends TableImpl<DatumRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>Datum</code>
     */
    public static final Datum DATUM = new Datum();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<DatumRecord> getRecordType() {
        return DatumRecord.class;
    }

    /**
     * The column <code>Datum.ID</code>.
     */
    public final TableField<DatumRecord, Integer> ID = createField(DSL.name("ID"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>Datum.datum</code>.
     */
    public final TableField<DatumRecord, LocalDate> DATUM_ = createField(DSL.name("datum"), SQLDataType.LOCALDATE, this, "");

    private Datum(Name alias, Table<DatumRecord> aliased) {
        this(alias, aliased, null);
    }

    private Datum(Name alias, Table<DatumRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>Datum</code> table reference
     */
    public Datum(String alias) {
        this(DSL.name(alias), DATUM);
    }

    /**
     * Create an aliased <code>Datum</code> table reference
     */
    public Datum(Name alias) {
        this(alias, DATUM);
    }

    /**
     * Create a <code>Datum</code> table reference
     */
    public Datum() {
        this(DSL.name("Datum"), null);
    }

    public <O extends Record> Datum(Table<O> child, ForeignKey<O, DatumRecord> key) {
        super(child, key, DATUM);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    public Identity<DatumRecord, Integer> getIdentity() {
        return (Identity<DatumRecord, Integer>) super.getIdentity();
    }

    @Override
    public UniqueKey<DatumRecord> getPrimaryKey() {
        return Keys.DATUM__;
    }

    @Override
    public Datum as(String alias) {
        return new Datum(DSL.name(alias), this);
    }

    @Override
    public Datum as(Name alias) {
        return new Datum(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Datum rename(String name) {
        return new Datum(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Datum rename(Name name) {
        return new Datum(name, null);
    }

    // -------------------------------------------------------------------------
    // Row2 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row2<Integer, LocalDate> fieldsRow() {
        return (Row2) super.fieldsRow();
    }
}
