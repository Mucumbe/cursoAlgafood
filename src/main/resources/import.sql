insert into cozinha (nome) values ('Tailandeza')
insert into cozinha (nome) values ('Indiana')
insert into cozinha (nome) values ('Mocambicana')

insert into estado (nome) values ('MG')
insert into estado (nome) values ('RJ')

insert into cidade (nome,estado_id) values ('Belo Horizonte',1)
insert into cidade (nome,estado_id) values ('Rio de Janeiro',2)

insert into restaurante (endereco_bairro,endereco_cep,endereco_logradouro,endereco_numero,nome,tx_frete,cozinha_id,endereco_cidade_id,data_actualizacao,data_cadastro) VALUES ('Centro', '38400-99', 'Rua Joao Pinheiro', '1000', 'Thai Gourmet', '24', '3', '1',utc_timestamp,utc_timestamp);
insert into restaurante (nome,tx_Frete,cozinha_id,data_actualizacao,data_cadastro) values ('A3',10,1,utc_timestamp,utc_timestamp)
insert into restaurante (nome,tx_Frete, cozinha_id,data_actualizacao,data_cadastro) values ('Galaxy',12,2,utc_timestamp,utc_timestamp)
insert into restaurante (nome,tx_Frete,cozinha_id,data_actualizacao,data_cadastro) values ('KFC',7,3,utc_timestamp,utc_timestamp)
insert into restaurante (nome,tx_Frete, cozinha_id,data_actualizacao,data_cadastro) values ('Mundos',14,3,utc_timestamp,utc_timestamp)





INSERT INTO `algafood`.`forma_pagamento` (`descricao`) VALUES ('Cartao de Debito');
INSERT INTO `algafood`.`forma_pagamento` (`descricao`) VALUES ('Cartao de Debito');
INSERT INTO `algafood`.`forma_pagamento` (`descricao`) VALUES ('Dinheiro');


insert into restaurante_forma_pagamento (restaurante_id,forma_pagamento_id) values (1,1)
insert into restaurante_forma_pagamento (restaurante_id,forma_pagamento_id) values (1,2)
insert into restaurante_forma_pagamento (restaurante_id,forma_pagamento_id) values (1,3)

insert into restaurante_forma_pagamento (restaurante_id,forma_pagamento_id) values (2,1)
insert into restaurante_forma_pagamento (restaurante_id,forma_pagamento_id) values (2,2)
insert into restaurante_forma_pagamento (restaurante_id,forma_pagamento_id) values (2,3)

insert into restaurante_forma_pagamento (restaurante_id,forma_pagamento_id) values (3,1)
insert into restaurante_forma_pagamento (restaurante_id,forma_pagamento_id) values (3,2)
insert into restaurante_forma_pagamento (restaurante_id,forma_pagamento_id) values (3,3)

insert into restaurante_forma_pagamento (restaurante_id,forma_pagamento_id) values (4,1)
insert into restaurante_forma_pagamento (restaurante_id,forma_pagamento_id) values (4,2)
insert into restaurante_forma_pagamento (restaurante_id,forma_pagamento_id) values (4,3)
