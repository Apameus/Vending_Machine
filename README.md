# Vending Machine

## Functional Requirements

### List all products
```
List all products in a table with columns
ID | NAME | PRICE | QUANTITY 
```

### Retrieve product
```
Input: ID, MONEY

Output:
  ID_NOT_FOUND
  NO_STOCK
  NOT_ENOUGH_MONEY
  SUCCESS ( change )
```

### Update products
```
*Authorized User
Can update products: ID, NAME, PRICE, QUANTITY
```

### Get all money inside the machine
```
Input: Authorized User, Command
```

### Analytics
```
Top 3 most brough products
Total Money
```

## Technical Documentation

### High Level Architecture
```mermaid
graph LR
UI[TerminalUI] --> PS[ProductService] --> PR[ProductRepository]
UI --> AS[AnalyticService] --> AR[AnalyticRepository] 
UI --> AUTHS[AuthorizationService] --> AUTHR[AuthorizationRepository]
PS --> AR
```

### List all products
```mermaid
sequenceDiagram
participant UI
participant S as ProductService
participant R as ProductRepository
UI ->> S: listProducts()
S ->> R: findAllProducts()
R ->> S: Product[]
S ->> UI: Product[]
```

### Retrieve product
```mermaid
sequenceDiagram
participant UI
participant PS as ProductService
participant PR as ProductRepository
participant AR as AnalyticRepository

UI ->> UI: id
UI ->> PS: verifyProductAvailability( id )
PS ->> PR: findProductById( id )
PR ->> PS: Product
        
alt Product not found
    PS ->> UI: ProductNotFoundException
end
alt Product.quantity == 0
    PS ->> UI: ZeroStockException
end
UI ->> UI: money


UI ->> PS: retrieveProduct( id, money )
PS ->> PR: findProductById( id )
PR ->> PS: Product
alt money < Product price
    PS ->> UI: NotEnoughMoneyException
end

PS ->> PR: decreaseQuantity( id )
PS ->> AR: increaseTotalAmountBy( money - change )
PS ->> PS: calculateChange( money - change )
PS ->> UI: Product, change
UI ->> UI: showMsg( success / fail )   
```

### Update products

#### Step 1. Authorize
```mermaid
sequenceDiagram
participant UI
participant AS as AuthorizationService
participant AR as AuthorizationRepository
        
UI ->> UI: userId (16 digits)
UI ->> UI: password
UI ->> AS: authorizeUser ( userId password )
AS ->> AR: findUserBy( userId )
AR ->> AS: User
alt User not found OR User.password != password
    AS ->> UI: AuthorizationFailedException
end
```

#### Command Table
| ID | ACTION                      |
|----|-----------------------------|
| 00 | exit()                      |
| 01 | updateQuantity()            |
| 02 | updatePrice()               |
| 03 | updateId()                  |
| 04 | updateName()                |
| 10 | addProduct()                |
| 20 | removeProduct()             |
| 30 | retreiveMoney               |
| 40 | topThreeMostSellingProducts |


#### Step 2.


## Commands
```mermaid
sequenceDiagram
participant UI
participant PS as ProductService
participant AR as AnalyticRepo
        
UI --> UI: Welcome
loop cmd != 00 <br> (terminate)
    UI ->> UI: cmd
    alt cmd == 01
        UI ->> UI: id
        UI ->> UI: quantity
        UI ->> PS: updateQuantity( id, quantity )
        PS ->> UI: true / false
        UI ->> UI: "Product updated successfully"
    end
    alt cmd == 02
        UI ->> UI: id
        UI ->> UI: price
        UI ->> PS: updatePrice ( id, price )
        PS ->> UI: true / false
        UI ->> UI: "Product updated successfully"
    end
        
    Note right of UI: / updateId & updateName /
        
    alt cmd == 10
        UI ->> PS: retrieveAllEarnings( AuthorisedUser )
        PS ->> AR: retrieveAllEarnings( AuthorisedUser )
        AR ->> PS: totalEarnings
        PS ->> UI: totalEarnings
        UI ->> UI: "Earnings retrieved"
        Note right of AR: track data of authorized user <br> retrieving this amount of earnings 
        AR ->> AR: totalEarnings = 0
    end
        
    alt cmd == 20
            UI ->> PS: topThreeProducts()
            PS ->> AR: topThreeProducts()
            AR ->> PS: list[product, sales]
            PS ->> UI: list[product, sales]
            UI ->> UI: show top products
    end
end
```


