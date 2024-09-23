package com.teste;

  
	import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
	



    
	@ManagedBean(name="tarefaBean")
	@ViewScoped
	

	public class Tarefa {
		private Integer id;
	    private String titulo;
	    private String descricao;
	    private String responsavel;
	    private Prioridade prioridade;
	    private String deadline;
	    private String situacao = "Andamento"; // Valor padrão

	    
	   
	    // Construtor padrão necessário para JSF
	    public Tarefa() {
	    }
	    
	    public String getSituacao() {
	        return situacao;
	    }

	    public void setSituacao(String situacao) {
	        this.situacao = situacao;
	    }
	   

		// Enum para representar as prioridades
	    public enum Prioridade {
	        ALTA, MEDIA, BAIXA
	    }
	    
	    public Integer getId() {
	        return id;
	    }

	    public void setId(Integer id) {
	        this.id = id;
	    }

		public String getTitulo() {
			return titulo;
		}

		public void setTitulo(String titulo) {
			this.titulo = titulo;
		}

		public String getDescricao() {
			return descricao;
		}

		public void setDescricao(String descricao) {
			this.descricao = descricao;
		}

		public String getResponsavel() {
			return responsavel;
		}

		public void setResponsavel(String responsavel) {
			this.responsavel = responsavel;
		}

		public Prioridade getPrioridade() {
			return prioridade;
		}

		public void setPrioridade(Prioridade prioridade) {
			this.prioridade = prioridade;
		}

		public String getDeadline() {
			return deadline;
		}

		public void setDeadline(String deadline) {
		    this.deadline = deadline;
		}
	    
	    
	    
	    
}
