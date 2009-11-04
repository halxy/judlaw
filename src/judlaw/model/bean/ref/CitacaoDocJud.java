package judlaw.model.bean.ref;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "citacaodocjud")
@SequenceGenerator(name = "citacaodocjud_seq", sequenceName = "citacaodocjud_seq", initialValue = 1, allocationSize = 1)
public class CitacaoDocJud extends Referencia {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="citacaodocjud_seq")
	@Column(name="citacaodocjud_id")
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
