package com.krovvidi.api.eventapi.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.krovvidi.api.eventapi.model.Event;

@Repository
public class EventAPIRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Transactional(readOnly = true)
	public List<Event> getEvents(Map<String, String> requestParams) throws Exception {
		return jdbcTemplate.query(buildQuery(requestParams), new EventAPIRowMapper());
	}

	private String buildQuery(Map<String, String> requestParams) {
		boolean isWhereClauseUsed = false;
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append(" SELECT event_date, event_month, event_year, event_type, event_group ");
		queryBuilder.append(" , first_name, last_name, maiden_name, email_id, phone_number, zip_code ");
		queryBuilder.append(" FROM event ");
		if (requestParams != null && !requestParams.isEmpty()) {
			String firstName = requestParams.get("firstName");
			if (firstName != null && firstName.trim().length() > 0) {
				queryBuilder.append(buildWhereClause(isWhereClauseUsed));
				isWhereClauseUsed = true;
				queryBuilder.append(" first_name = '");
				queryBuilder.append(firstName);
				queryBuilder.append("'");
			}
			String lastName = requestParams.get("lastName");
			if (lastName != null && lastName.trim().length() > 0) {
				queryBuilder.append(buildWhereClause(isWhereClauseUsed));
				isWhereClauseUsed = true;
				queryBuilder.append(" last_name = '");
				queryBuilder.append(lastName);
				queryBuilder.append("'");
			}
			String maidenName = requestParams.get("maidenName");
			if (maidenName != null && maidenName.trim().length() > 0) {
				queryBuilder.append(buildWhereClause(isWhereClauseUsed));
				isWhereClauseUsed = true;
				queryBuilder.append(" maiden_name = '");
				queryBuilder.append(maidenName);
				queryBuilder.append("'");
			}
			String zipCode = requestParams.get("zipCode");
			if (zipCode != null && zipCode.trim().length() > 0) {
				queryBuilder.append(buildWhereClause(isWhereClauseUsed));
				isWhereClauseUsed = true;
				queryBuilder.append(" zip_code = '");
				queryBuilder.append(zipCode);
				queryBuilder.append("'");
			}
		}
		return queryBuilder.toString();
	}

	private String buildWhereClause(boolean isWhereClauseUsed) {
		String whereClause = " WHERE ";
		if (isWhereClauseUsed) {
			whereClause = " AND ";
		}
		return whereClause;
	}
}

class EventAPIRowMapper implements RowMapper<Event> {

	@Override
	public Event mapRow(ResultSet rs, int rowNum) throws SQLException {
		Event event = new Event();
		event.setEventDate(rs.getInt("event_date"));
		event.setEventMonth(rs.getInt("event_month"));
		event.setEventYear(rs.getInt("event_year"));
		event.setEventType(rs.getString("event_type"));
		event.setEventGroup(rs.getString("event_group"));
		event.setFirstName(rs.getString("first_name"));
		event.setLastName(rs.getString("last_name"));
		event.setMaidenName(rs.getString("maiden_name"));
		event.setEmailId(rs.getString("email_id"));
		event.setPhoneNumber(rs.getString("phone_number"));
		event.setZipCode(rs.getString("zip_code"));
		return event;
	}

}
