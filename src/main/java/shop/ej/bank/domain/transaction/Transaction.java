package shop.ej.bank.domain.transaction;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.ej.bank.domain.account.Account;

@NoArgsConstructor
@Getter
@EntityListeners(AuditingEntityListener.class)
@Table(name = "transaction_tb")
@Entity
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Account withdrawAccount;

	@ManyToOne(fetch = FetchType.LAZY)
	private Account depositAccount;

	@Column(nullable = false)
	private Long amount;

	private Long withdrawAccountBalance;
	private Long depositAccountBalance;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private TransactionEnum type; // WITHDRAW, DEPOSIT, TRANSFER, ALL

	private String sender;
	private String receiver;
	private String tel;

	@CreatedDate
	@Column(nullable = false)
	private LocalDateTime createdAt;

	@LastModifiedDate
	@Column(nullable = false)
	private LocalDateTime updatedAt;

	@Builder
	public Transaction(Long id, Account withdrawAccount, Account depositAccount, Long amount,
		Long withdrawAccountBalance,
		Long depositAccountBalance, TransactionEnum type, String sender, String receiver, String tel,
		LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.id = id;
		this.withdrawAccount = withdrawAccount;
		this.depositAccount = depositAccount;
		this.amount = amount;
		this.withdrawAccountBalance = withdrawAccountBalance;
		this.depositAccountBalance = depositAccountBalance;
		this.type = type;
		this.sender = sender;
		this.receiver = receiver;
		this.tel = tel;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
}
