package com.example.demo.dto.request;

import java.util.List;

public class StateLevel {

	private String state;
	private List<DistrictLevel> districts;

	// ===== Getters and Setters =====
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<DistrictLevel> getDistricts() {
		return districts;
	}

	public void setDistricts(List<DistrictLevel> districts) {
		this.districts = districts;
	}

	// ===== Nested Classes =====
	public static class DistrictLevel {
		private String district;
		private List<SubDistrictLevel> subDistricts;

		public String getDistrict() {
			return district;
		}

		public void setDistrict(String district) {
			this.district = district;
		}

		public List<SubDistrictLevel> getSubDistricts() {
			return subDistricts;
		}

		public void setSubDistricts(List<SubDistrictLevel> subDistricts) {
			this.subDistricts = subDistricts;
		}
	}

	public static class SubDistrictLevel {
		private String subDistrict;
		private List<String> villages;

		public String getSubDistrict() {
			return subDistrict;
		}

		public void setSubDistrict(String subDistrict) {
			this.subDistrict = subDistrict;
		}

		public List<String> getVillages() {
			return villages;
		}

		public void setVillages(List<String> villages) {
			this.villages = villages;
		}
	}
}
