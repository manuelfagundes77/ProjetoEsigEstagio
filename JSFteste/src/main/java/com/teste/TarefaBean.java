package com.teste;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;

import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ManagedBean(name = "tarefaControle")
@SessionScoped

//Classe para criar as funções 
public class TarefaBean {
	
    private Tarefa tarefa = new Tarefa(); // Objeto tarefa que será preenchido no formulário
    private List<Tarefa> listaDeTarefas; // Lista para armazenar as tarefas
    
 // Filtros
    private String idFiltro;
    private String descricaoFiltro;
    private String responsavelFiltro;
    private String situacaoFiltro;
    
    private int idTarefaSelecionada; // Variável para armazenar o ID da tarefa selecionada
    
    

    // Metodo para cadastrar a tarefa
    public String cadastrarTarefa() {
        System.out.println("Iniciando o cadastro da tarefa...");

        SalvarTarefa salvarTarefa = new SalvarTarefa(); //criar instancia do objeto 
        System.out.println("Título: " + tarefa.getTitulo());
        System.out.println("Descrição: " + tarefa.getDescricao());
        System.out.println("Responsável: " + tarefa.getResponsavel());
        System.out.println("Prioridade: " + tarefa.getPrioridade());
        System.out.println("Deadline: " + tarefa.getDeadline());
        
       

        // Verifica se o ID da tarefa e nulo ou zero
        if (tarefa.getId() == null || tarefa.getId() <= 0) {
            // ID nao existe cadastra nova tarefa
         // Define a situaçao padrao como "Andamento"
            tarefa.setSituacao("Andamento"); 
        	
            salvarTarefa.salvarTarefa(tarefa);//Chama a função para cadastra a tarefa no Banco de dados.
            System.out.println("Tarefa cadastrada com sucesso.");
            
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, " Tarefa cadastrada com sucesso! ", "Tarefa cadastrada com sucesso!"));
        } else {
            // ID existe, atualiza a tarefa
            salvarTarefa.atualizarTarefa(tarefa);
            System.out.println("Tarefa atualizada com sucesso.");
            carregarTarefas();
          
            return "cadastradas.xhtml?faces-redirect=true";
        }

        // Limpar o objeto tarefa apos salvar/atualizar
        tarefa = new Tarefa();
        idTarefaSelecionada = 0; // limpar o campo  para nao levar o id para outras paginas
        System.out.println("Objeto tarefa limpo.");
        
        carregarTarefas(); // Atualiza a lista de tarefas
        System.out.println("Lista de tarefas carregada. Salva");
        return null;
    }
    
    public String redirecionar(String a) {
    	System.out.println("Ieu funciono?");
        return "cadastradas.xhtml?faces-redirect=true"; // Redireciona para a página de tarefas cadastradas
    }

    

    @PostConstruct
    public void init() {  
    	System.out.println("Inicializando TarefaControle...");
        carregarTarefas(); // Carrega as tarefas ao iniciar o bean   
        if (idTarefaSelecionada > 0) {
        	System.out.println("aqui init");
            carregarTarefaPorId(idTarefaSelecionada); // Carrega a tarefa se um ID for selecionado
        }
        
    }
    
    //funções para debugar 
    public void imprimirId() {
        if (tarefa != null) {
            System.out.println("ID da tarefa: " + tarefa.getId());
            
        } else {
            System.out.println("Nenhuma tarefa carregada.");
        }
    }
    
    
    //Criada para salvar o id ao apertar o botão edit e levar o numero para a outra pagina
    public String navegarParaEditar(int id) {
        this.idTarefaSelecionada = id; // Armazena o ID da tarefa
        System.out.println("Editando tarefa com ID: " + id);
        carregarTarefaPorId(id); // Carrega a tarefa aqui
        return "editar.xhtml?faces-redirect=true"; // Redireciona
    }
    
    
    //para carregar os dados na pagina edit
    public void carregarTarefaPorId(int id) {
        SalvarTarefa salvarTarefa = new SalvarTarefa();
        this.tarefa = salvarTarefa.buscarTarefaPorId(id);
        if (tarefa != null) {
            System.out.println("Tarefa carregada para edição: " + tarefa.getTitulo());
        } else {
            System.out.println("Tarefa não encontrada com ID: " + id);
        }
    }
    
    
    
   
    public void carregarTarefas() {
        System.out.println("Carregando tarefas...");
        SalvarTarefa salvarTarefa = new SalvarTarefa();
        this.listaDeTarefas = salvarTarefa.listarTarefas(); // Carrega a lista de tarefas
        System.out.println("Tarefas carregadas.");

        // Exibir as tarefas carregadas no console
		/*
		 * for (Tarefa tarefa : listaDeTarefas) { System.out.println("ID: " +
		 * tarefa.getId()); // Imprime o ID System.out.println("Título: " +
		 * tarefa.getTitulo()); System.out.println("Descrição: " +
		 * tarefa.getDescricao()); System.out.println("Responsável: " +
		 * tarefa.getResponsavel()); System.out.println("Prioridade: " +
		 * tarefa.getPrioridade()); System.out.println("Deadline: " +
		 * tarefa.getDeadline()); System.out.println("---------------------------"); }
		 */
    }
    
    

     
    
    public void excluirTarefa(int id) {
        System.out.println("Excluindo a tarefa com ID: " + id);

        SalvarTarefa salvarTarefa = new SalvarTarefa();
        salvarTarefa.excluirTarefa(id); // Chama o método de exclusão no banco

        // Após a exclusão, recarregar a lista de tarefas
        carregarTarefas();
    }
    
    
    public void filtrarTarefas() {
        System.out.println("Filtrando tarefas...");
        SalvarTarefa salvarTarefa = new SalvarTarefa();
        List<Tarefa> todasTarefas = salvarTarefa.listarTarefas(); // Carrega todas as tarefas
        this.listaDeTarefas = new ArrayList<>(todasTarefas); // Inicie com todas as tarefas

       
        // Filtrar tarefas com base nos critérios preenchidos
        // Verifica se o filtro de ID não é nulo e não está vazio
        if (idFiltro != null && !idFiltro.isEmpty()) {
        	 // Aplica o filtro de ID usando streams
            listaDeTarefas = listaDeTarefas.stream()
                .filter(t -> t.getId().toString().contains(idFiltro)) // Filtra tarefas cujo ID contém o valor do filtro
                .collect(Collectors.toList()); // Coleta os resultados filtrados em uma nova lista
        }
        if (descricaoFiltro != null && !descricaoFiltro.isEmpty()) {
            listaDeTarefas = listaDeTarefas.stream()
                .filter(t -> t.getDescricao().toLowerCase().contains(descricaoFiltro.toLowerCase()))
                .collect(Collectors.toList());
        }
        if (responsavelFiltro != null && !responsavelFiltro.isEmpty()) {
            listaDeTarefas = listaDeTarefas.stream()
                .filter(t -> t.getResponsavel().equals(responsavelFiltro))
                .collect(Collectors.toList());
        }
        if (situacaoFiltro != null && !situacaoFiltro.isEmpty()) {
            listaDeTarefas = listaDeTarefas.stream()
                .filter(t -> t.getSituacao().equals(situacaoFiltro))
                .collect(Collectors.toList());
        }

        System.out.println("Tarefas filtradas.");
    }

    
    public void concluirTarefa(int id) {
        System.out.println("Concluindo a tarefa com ID: " + id);
        
        SalvarTarefa salvarTarefa = new SalvarTarefa();
        salvarTarefa.concluirTarefa(id); // Método que você implementará para atualizar no banco

        // Atualiza a lista de tarefas
        carregarTarefas();
    }
    
    
    
    
    
   
    // Getters e Setters para o objeto tarefa
    public Tarefa getTarefa() {
        return tarefa;
    }

    public void setTarefa(Tarefa tarefa) {
        this.tarefa = tarefa;
    }

    public List<Tarefa> getListaDeTarefas() {
        return listaDeTarefas;
    }

    // Getters e Setters para os filtros
    public String getIdFiltro() {
        return idFiltro;
    }

    public void setIdFiltro(String idFiltro) {
        this.idFiltro = idFiltro;
    }

    public String getDescricaoFiltro() {
        return descricaoFiltro;
    }

    public void setDescricaoFiltro(String descricaoFiltro) {
        this.descricaoFiltro = descricaoFiltro;
    }

    public String getResponsavelFiltro() {
        return responsavelFiltro;
    }

    public void setResponsavelFiltro(String responsavelFiltro) {
        this.responsavelFiltro = responsavelFiltro;
    }

    public String getSituacaoFiltro() {
        return situacaoFiltro;
    }

    public void setSituacaoFiltro(String situacaoFiltro) {
        this.situacaoFiltro = situacaoFiltro;
    }
    
    
}
