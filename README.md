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
| 30 | retrieveMoney               |
| 40 | topThreeMostSellingProducts |


#### Step 2.


## Commands

### exit
```mermaid
sequenceDiagram
UI --> UI: Welcome
loop cmd != 00 
UI ->> UI: cmd
end
```


### updateQuantity
```mermaid
sequenceDiagram
    participant UI
    participant PS as ProductService
    participant PR as ProductRepo 
    
alt cmd == 01
UI ->> UI: id
UI ->> UI: quantity
UI ->> PS: updateQuantity( id, quantity )
PS ->> PR: updateQuantity ( id, quantity )
    PR ->> PS: Product
    alt Product.quantity() + quantity <= 10
        PS ->> UI: machineOverloadedException
    end
    alt invalid id
    PS ->> UI: productNotFoundException
end
UI ->> UI: "Product updated successfully"
end
```

### updatePrice
```mermaid
sequenceDiagram
    participant UI
    participant PS as ProductService
    participant PR as ProductRepo
    
    alt cmd == 02
        UI ->> UI: id
        UI ->> UI: price
        UI ->> PS: updatePrice ( id, price )
        PS ->> PR: updatePrice ( id, price )
        PR ->> PS: Product
        alt invalid id
                PS ->> UI: productNotFoundException
        end
        UI ->> UI: "Product updated successfully"
    end

```

### updateId
```mermaid
sequenceDiagram
    participant UI
    participant PS as ProductService
    participant PR as ProductRepo

    alt cmd == 03
        UI ->> UI: id
        UI ->> UI: newId
        UI ->> PS: updateId ( id, newId )
        PS ->> PR: updateId ( id, newId )
        PR ->> PS: Product
        alt invalid id
            PS ->> UI: productNotFoundException
        end
        UI ->> UI: "Product updated successfully"
    end

```

### updateName
```mermaid
sequenceDiagram
    participant UI
    participant PS as ProductService
    participant PR as ProductRepo
    
    alt cmd == 04
        UI ->> UI: id
        UI ->> UI: name
        UI ->> PS: updateName ( id, name )
        PS ->> PR: updateName ( id, name )
        PR ->> PS: Product
        alt invalid id
            PS ->> UI: productNotFoundException
        end
        UI ->> UI: "Product updated successfully"
    end

```

### addProduct
```mermaid
sequenceDiagram
    participant UI
    participant PS as ProductService
    participant PR as ProductRepo
    alt cmd == 10
        UI ->> PS: removeProduct( id )
        PS ->> PR: removeProduct( id )
    end

```

### removeProduct
```mermaid
sequenceDiagram
participant UI
participant PS as ProductService
participant PR as ProductRepo
alt cmd == 20 
    UI ->> PS: removeProduct( id )
        PS ->> PR: removeProduct( id )
end
```

### retrieveMoney
```mermaid
sequenceDiagram
    participant UI
    participant PS as ProductService
    participant AR as AnalyticRepo


    alt cmd == 30
        UI ->> PS: retrieveAllEarnings( AuthorisedUser )
        PS ->> AR: retrieveAllEarnings( AuthorisedUser )
        AR ->> PS: totalEarnings
        PS ->> UI: totalEarnings
        UI ->> UI: "Earnings retrieved"
        Note right of AR: track data of authorized user <br> retrieving this amount of earnings
        AR ->> AR: totalEarnings = 0
    end
```

### TopThreeProducts
```mermaid
sequenceDiagram
    participant UI
    participant PS as ProductService
    participant AR as AnalyticRepo

    alt cmd == 40
            UI ->> PS: topThreeProducts()
            PS ->> AR: topThreeProducts()
            AR ->> PS: list[product, sales]
            PS ->> UI: list[product, sales]
    end
```


