package br.com.zup.transacao.transacoes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/transacoes")
public class TransacaoController {

    @Autowired
    private TransacaoRepository transacaoRepository;
    @Autowired
    private CartaoRepository cartaoRepository;

    @GetMapping("/{idCartao}")
    public ResponseEntity<Page<Transacao>> listar(@PathVariable String idCartao,
                                                  @PageableDefault(size = 5) Pageable paginacao) {

        if (!cartaoRepository.existsById(idCartao)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cartão não encontrado");
        }

        Page<Transacao> transacoes = transacaoRepository.findAllByCartaoId(idCartao, paginacao);

        return ResponseEntity.ok().body(transacoes);
    }
}
