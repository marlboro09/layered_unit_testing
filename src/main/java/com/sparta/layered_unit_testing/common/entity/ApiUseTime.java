package com.sparta.layered_unit_testing.common.entity;

import com.sparta.layered_unit_testing.domain.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "api_use_time")
public class ApiUseTime {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Column(nullable = false)
	private Long totaltime;

	@Builder
	public ApiUseTime(User user, Long totaltime) {
		this.user = user;
		this.totaltime = totaltime;
	}

	public void addUseTime(long useTime) {
		this.totaltime += useTime;
	}
}
