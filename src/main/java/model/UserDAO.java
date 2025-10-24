package model;

import java.sql.*;
import util.PasswordUtil;

public class UserDAO {

	public UserDTO findById(String userId) {
		String sql = "SELECT USER_ID, USER_PW_HASH, USER_NAME, EMAIL FROM USERS WHERE USER_ID = ?";
		try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, userId);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					UserDTO dto = new UserDTO();
					dto.setUserId(rs.getString("USER_ID"));
					dto.setUserPwHash(rs.getString("USER_PW_HASH"));
					dto.setUserName(rs.getString("USER_NAME"));
					dto.setEmail(rs.getString("EMAIL"));
					return dto;
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("findById error", e);
		}
		return null;
	}

	/** 평문 비밀번호와 해시 비교 */
	public boolean checkLogin(String userId, String rawPw) {
		UserDTO user = findById(userId);
		if (user == null)
			return false;
		String inputHash = PasswordUtil.sha256(rawPw);
		return inputHash.equalsIgnoreCase(user.getUserPwHash());
	}
}
