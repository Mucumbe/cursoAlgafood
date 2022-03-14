insert into cozinha (nome) values ('Tailandeza')
insert into cozinha (nome) values ('Indiana')
insert into cozinha (nome) values ('Mocambicana')

insert into restaurante (nome,tx_Frete,cozinha_id) values ('A3',10,1)
insert into restaurante (nome,tx_Frete, cozinha_id) values ('Galaxy',12,2)
insert into restaurante (nome,tx_Frete,cozinha_id) values ('KFC',7,3)
insert into restaurante (nome,tx_Frete, cozinha_id) values ('Mundos',14,3)



insert into estado (nome) values ('MG')
insert into estado (nome) values ('RJ')

insert into cidade (nome,estado_id) values ('Belo Horizonte',1)
insert into cidade (nome,estado_id) values ('Rio de Janeiro',2)

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
