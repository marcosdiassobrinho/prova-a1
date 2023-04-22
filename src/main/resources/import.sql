INSERT INTO login (id, email, login, password) VALUES (1 , 'wesley@gmail.com', 'palmas121', 'senhainventada');
insert into perfil (id, data_criacao, login_id, status_perfil) values (1, now(), 1, 'COMPLETO');


INSERT INTO login (id, email, login, password) VALUES (2 , 'marcos@gmail.com', 'marcosLindo', 'heheSouLindo');
INSERT INTO perfil (id, data_criacao, login_id, status_perfil) values (2, now(), 2, 'COMPLETO');

INSERT INTO usuario (id, nome, cpf, data_de_nascimento) VALUES
(1, 'Wesley Dias', '12345678900', '1990-01-01'),
(2, 'Marcos Dias', '98765432100', '1995-05-05');

INSERT INTO carrinho (id, perfil_id) VALUES(1,1),(2,2);

UPDATE perfil SET usuario_id = 1 WHERE id = 1;
UPDATE perfil SET usuario_id = 2 WHERE id = 2;



INSERT INTO comentario_perfil (comentario, denunciado, perfil_id, data_comentario) VALUES ('É O BRAIAN', false, 1, '2023-03-20 00:00:10.000000');
INSERT INTO comentario_perfil (comentario, denunciado, perfil_id, data_comentario) VALUES ('SÉRIO ELE É BRABO', false, 1, '2023-03-20 00:00:20.000000');
INSERT INTO comentario_perfil (comentario, denunciado, perfil_id, data_comentario) VALUES ('Você também!', false, 2, '2023-03-21 00:00:10.000000');


INSERT INTO marca(nome) VALUES ('7hz');
INSERT INTO marca(nome) VALUES ('Letshuer');
INSERT INTO marca(nome) VALUES ('Moondrop');
INSERT INTO marca(nome) VALUES ('Kz');
INSERT INTO marca(nome) VALUES ('Truthear');

INSERT INTO produto(nome, marca_id) VALUES ('Timeless', 1);
INSERT INTO produto(nome, marca_id) VALUES ('Dioko', 1);
INSERT INTO produto(nome, marca_id) VALUES ('Zero', 1);

INSERT INTO produto(nome, marca_id) VALUES ('S12',2);
INSERT INTO produto(nome, marca_id) VALUES ('D13',2);

INSERT INTO produto(nome, marca_id) VALUES ('Quarks',3);
INSERT INTO produto(nome, marca_id) VALUES ('Chú',3);
INSERT INTO produto(nome, marca_id) VALUES ('Aria',3);
INSERT INTO produto(nome, marca_id) VALUES ('Kato',3);

INSERT INTO produto(nome, marca_id) VALUES ('ZEX PRO',4);

INSERT INTO produto(nome, marca_id) VALUES ('Zero crn',5);
INSERT INTO produto(nome, marca_id) VALUES ('Hexa',5);


-- Inserir dados na tabela pagina_produto
INSERT INTO anuncio(ativo, perfil_id, titulo, produto_id) VALUES
(true, 1, '7hz Timeless planar-magnetico 14.2mm', 1);
INSERT INTO anuncio(ativo, perfil_id, titulo, produto_id) VALUES
(true, 2, 'Letshuer D13', 2);

-- Inserir dados na tabela produto_variacao
INSERT INTO variacao(descricao, peso, quantidade_estoque, usado, valor_liquido,valor_bruto ) VALUES
('Timeless 4.4 balanceado', 0.5, 10, false, 1000.00, 900.00 ),
('Timeless 3.5', 0.5, 5, false, 1000.00, 900.00),
('Timeless AE', 0.7, 5, false, 1260.00, 900.00);


INSERT INTO variacao(descricao, peso, quantidade_estoque, usado, valor_liquido,valor_bruto ) VALUES
('D13 PRO', 0.5, 10, false, 500.00, 550.00 ),
('D13', 0.5, 5, false, 400.00, 440.00);

INSERT INTO produto_variacao(produto_id, variacao_id) VALUES
 (1, 1),
 (1, 2),
 (1, 3),
 (2, 4),
 (2, 5);


INSERT INTO bate_papo (anuncio_id, perfil_comprador) VALUES (1,2);

INSERT INTO comentario_bate_papo(comentario, datacriacao, denunciado, vendedor, bate_papo_id, perfil_id)
VALUES('Tá disponivel?', now(), false, false, 1, 2), ('Tá sim', now(), false, true, 1, 1);





