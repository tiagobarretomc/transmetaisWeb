ALTER TABLE `transmetais`.`compra` 
ADD COLUMN `data_vencimento` DATETIME NULL AFTER `modalidade_pagamento`,
ADD COLUMN `data_pagamento` DATETIME NULL AFTER `data_vencimento`;
