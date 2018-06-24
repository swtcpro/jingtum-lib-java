# jingtum-lib-java  

## 1、项目介绍  

    这是一个java语言开发的智能合约接口项目。  
    
## 2、接口说明  

1）**创建钱包**  

   * 方法一：`Wallet.generate();`
   * 方法二：`Wallet.fromSecret(secret);`  
   
   参数：

参数 | 类型 | 说明
:----:|:------:|:----:
secret | String  | 井通钱包地址  

2）**创建连接,返回Remote对象**  
  
`Connection conn = ConnectionFactory.getCollection(server);`

`Remote remote = new Remote(conn);`  

   参数：

参数 | 类型 | 说明
:----:|:------:|:----:
server | String  | 井通底层服务地址  

3）**建立连接后，获取节点信息及帐本信息**  

`LedgerInfo res = remote.requestLedgerInfo();`  

4）**关闭连接**  

调用创建连接时Connection对象的close方法。  

`conn.close();`

5）**请求底层服务器信息**  

`ServerInfo bean = remote.requestServerInfo();`  

6）**获取最新账本信息**  

`LedgerClosed bean = remote.requestLedgerClosed();`  

7）**获取某一账本具体信息**  

`Ledger bean = remote.requestLedger(ledger_hash, transactions);`

   参数：

参数 | 类型 | 说明
:----:|:------:|:----:
ledger_index | String  | 井通区块高度  
ledger_hash | String  | 井通区块hash(与上面ledger_index二选其一)  
transactions | Boolean  | 是否返回账本上的交易记录hash，默认false  

8）**查询某一交易具体信息**  

`Account bean = remote.requestTx(hash);`

   参数：

参数 | 类型 | 说明
:----:|:------:|:----:
hash | String  | 交易hash 

9）**请求账号信息**  

`AccountInfo bean = remote.requestAccountInfo(account);`

   参数：

参数 | 类型 | 说明
:----:|:------:|:----:
account | String  | 井通钱包地址  

10）**获得账号可接收和发送的货币**  

`AccountTums bean = remote.requestAccountTums(account);`

   参数：

参数 | 类型 | 说明
:----:|:------:|:----:
account | String  | 井通钱包地址 

11）**获得账号关系**  

`AccountRelations bean = remote.requestAccountRelations(account, type);`

   参数：

参数 | 类型 | 说明
:----:|:------:|:----:
account | String  | 井通钱包地址
type | String  | 关系类型，固定的三个值:trust、authorize、freeze

12）**获得账号挂单**  

`AccountOffers bean = remote.requestAccountOffers(account);`

   参数：

参数 | 类型 | 说明
:----:|:------:|:----:
account | String  | 井通钱包地址

13）**获得账号交易列表**  

`AccountTx bean = remote.requestAccountTx(account, limit);`

   参数：

参数 | 类型 | 说明
:----:|:------:|:----:
account | String  | 井通钱包地址
limit | Integer  | 限定返回多少条记录，默认200

14）**获得市场挂单列表**  

`BookOffer bean = remote.requestOrderBook(gets, pays);`

   参数：

参数 | 类型 | 说明
:----:|:------:|:----:
gets | Amount  | 对家想要获得的货币信息,{value:'金额',currency:'货币种类',issuer:'货币'}
pays | Amount  | 对家想要支付的货币信息,{value:'金额',currency:'货币种类',issuer:'货币'}

14）**提交支付**  

`PaymentInfo bean = remote.buildPaymentTx(account, to, amount, memo, secret);`

   参数：

参数 | 类型 | 说明
:----:|:------:|:----:
account | String  | 发起账号
to | String  | 目标账号
amount | Amount  | 支付金额对象{value:'金额',currency:'货币种类',issuer:'货币'}
memo | String  | 备注
secret | String  | 密钥

15）**关系设置**  

`RelationInfo bean = remote.buildRelationTx(type, account, to, amount, memo, secret);`

   参数：

参数 | 类型 | 说明
:----:|:------:|:----:
type | String  | 关系种类
account | String  | 发起账号
to | String  | 目标账号
amount | Amount  | 目标账号{value:'金额',currency:'货币种类',issuer:'货币'}
memo | String  | 备注
secret | String  | 密钥

16）**设置账号属性**  

`AccountPropertyInfo bean = remote.buildAccountSetTx(type, account, code, memo, secret);`

   参数：

参数 | 类型 | 说明
:----:|:------:|:----:
type | String  | 属性种类
account | String  | 设置属性的源账号
code | String  | 属性编号
memo | String  | 备注
secret | String  | 密钥

17）**挂单**  

`OfferCreateInfo bean = remote.buildOfferCreateTx(type, account, getsAmount, paysAmount, memo, secret);`

   参数：

参数 | 类型 | 说明
:----:|:------:|:----:
type | String  | 挂单类型，固定的两个值：Buy、Sell
account | String  | 挂单方账号
getsAmount | Amount  | 对方得到的，即挂单方支付的{value:'金额',currency:'货币种类',issuer:'货币'}
paysAmount | Amount  | 对方支付的，即挂单方获得的{value:'金额',currency:'货币种类',issuer:'货币'}
secret | String  | 密钥

18）**取消挂单**  

`OfferCancelInfo bean = remote.buildOfferCancelTx(account, sequence, memo, secret);`

   参数：

参数 | 类型 | 说明
:----:|:------:|:----:
account | String  | 挂单方账号
sequence | Integer  | 取消的单子号
memo | String  | 备注
secret | String  | 密钥
