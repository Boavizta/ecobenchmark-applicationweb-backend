package com.ecobenchmark.entities;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

public class TimestampWithTimezone implements UserType {

    @Override
    public int[] sqlTypes() {
        return new int[] { Types.TIME_WITH_TIMEZONE };
    }

    @Override
    public Class returnedClass() {
        return Instant.class;
    }

    @Override
    public boolean equals(Object o, Object o1) throws HibernateException {
        return Objects.equals( o, o1);
    }

    @Override
    public int hashCode(Object o) throws HibernateException {
        return Objects.hashCode(o);
    }

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] names, SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException, SQLException {
        LocalDateTime localDateTime = resultSet.getObject( names[0], LocalDateTime.class );
        return localDateTime == null || resultSet.wasNull() ? null : localDateTime.toInstant(ZoneOffset.UTC);
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object value, int index, SharedSessionContractImplementor sharedSessionContractImplementor) throws HibernateException, SQLException {
        if ( value == null ) {
            preparedStatement.setNull( index, Types.TIMESTAMP_WITH_TIMEZONE );
        }
        else {
            OffsetDateTime offsetDateTime = ((Instant) value).atOffset(ZoneOffset.UTC);

            preparedStatement.setObject( index, offsetDateTime );
        }
    }

    @Override
    public Object deepCopy(Object o) throws HibernateException {
        return o;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(Object o) throws HibernateException {
        return (Instant) o;
    }

    @Override
    public Object assemble(Serializable serializable, Object o) throws HibernateException {
        return serializable;
    }

    @Override
    public Object replace(Object o, Object o1, Object o2) throws HibernateException {
        return o;
    }
}
