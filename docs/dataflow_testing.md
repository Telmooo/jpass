
## jpass.util.CryptUtils.getSha256Hash

![Dataflow](assets/GetSha256HashDataflow.png)

### Variable `text`
| **var** | **id** | **def** | **use** | **path**  |
| :-----: | :----: | :-----: | :-----: | :-------: |
|  text   |   1    |    1    |    4    | <1,2,3,4> |

- **all-defs**
  - The path with `id=1` covers all the criteria needed
- **all-c-uses**
  - The path with `id=1` covers all the criteria needed
- **all-p-uses**
  - Variable `text` doesn't have any p-use, therefore, there's no need to cover this criteria
- **all-uses**
  - The path with `id=1` covers all the criteria needed

### Variable `iteration`
|  **var**  | **id** | **def** | **use** |      **path**      |
| :-------: | :----: | :-----: | :-----: | :----------------: |
| iteration |   1    |    1    |  (7,F)  | <1,2,3,4,5,6,7,11> |
| iteration |   2    |    1    |  (7,T)  | <1,2,3,4,5,6,7,8>  |

- **all-defs**
  - The path with `id=1` covers all the criteria needed
- **all-c-uses**
  - Variable `iteration` doesn't have any c-use, therefore, there's no need to cover this criteria
- **all-p-uses**
  - To satisfy this testing criteria, all paths are necessary
- **all-uses**
  - To satisfy this testing criteria, all paths are necessary

### Variable `md`
| **var** | **id** | **def** | **use** |     **path**      |
| :-----: | :----: | :-----: | :-----: | :---------------: |
|   md    |   1    |    2    |    3    |       <2,3>       |
|   md    |   2    |    2    |    5    |     <2,3,4,5>     |
|   md    |   3    |    2    |    8    |  <2,3,4,5,6,7,8>  |
|   md    |   4    |    2    |    9    | <2,3,4,5,6,7,8,9> |

- **all-defs**
  - The path with `id=4` covers all the criteria needed, as the other paths are redundant
- **all-c-uses**
  - To cover this testing criteria, all paths are needed
- **all-p-uses**
  - Variable `md` doesn't have any p-uses, therefore, there's no need to cover this criteria
- **all-uses**
  - To cover this testing criteria, all paths are needed

### Variable `bytes`
| **var** | **id** | **def** | **use** | **path** |
| :-----: | :----: | :-----: | :-----: | :------: |
|  bytes  |   1    |    4    |    5    |  <4,5>   |

- **all-defs**
  - The path with `id=1` covers all the criteria needed
- **all-c-uses**
  - The path with `id=1` covers all the criteria needed
- **all-p-uses**
  - Variable `bytes` doesn't have any p-use, therefore, there's no need to cover this criteria
- **all-uses**
  - The path with `id=1` covers all the criteria needed

### Variable `digest`
| **var** | **id** | **def** | **use** |   **path**   |
| :-----: | :----: | :-----: | :-----: | :----------: |
| digest  |   1    |    5    |   11    |  <5,6,7,11>  |
| digest  |   2    |    5    |    9    | <5,6,7,8,9>  |
| digest  |   3    |    9    |    9    | <9,10,7,8,9> |
| digest  |   4    |    9    |   11    | <9,10,7,11>  |

- **all-defs**
  - The paths with `id=1` and `id=4` are enough to satisfy this testing criteria, with other paths being redundant
- **all-c-uses**
  - To satisfy this testing criteria, all paths are necessary
- **all-p-uses**
  - The variable `digest` doesn't have any p-uses, therefore, there's no need to cover this criteria
- **all-uses**
  - To satisfy this testing criteria, all paths are necessary

### Variable `i`
| **var** | **id** | **def** | **use** |   **path**    |
| :-----: | :----: | :-----: | :-----: | :-----------: |
|    i    |   1    |    6    |  (7,F)  |   <6,7,11>    |
|    i    |   2    |    6    |  (7,T)  |    <6,7,8>    |
|    i    |   3    |    6    |   10    | <6,7,8,9,10>  |
|    i    |   4    |   10    |   10    | <10,7,8,9,10> |
|    i    |   5    |   10    |  (7,F)  |   <10,7,11>   |
|    i    |   6    |   10    |  (7,T)  |   <10,7,8>    |

In path with `id=4` we opted to represent based on how it works in practice (Java) opposed to following literature, therefore, variable `i` is used before being defined.

- **all-defs**
  - The paths with `id=2` and `id=4` are enough to satisfy this testing criteria, with other paths being redundant
- **all-c-uses**
  - To satisfy this testing criteria, path with `id=3` and `id=4` are necessary, other paths are p-uses
- **all-p-uses**
  - To satisfy this testing criteria, path with `id=1`, `id=2`, `id=5` and `id=6` are necessary, other paths are c-uses
- **all-uses**
  - To satisfy this testing criteria, all paths are necessary

### Implemented Tests
This function is private, however, it can be tested via two public functions:
- `getSha256Hash` - overload that calculates the hash at `iteration=0`
- `getPKCS5Sha256Hash` - calculates the hash at `iteration=1000`

The tests already developed for the two functions above already cover all paths needed to be tested.
- Testing the function `getSha256Hash` tests the following paths for each variable:
  - `text` - All paths
  - `iteration` - Path with `id=1`
  - `md` - Covers the path `id=1` and `id=2` but these are redundant with the tests on the other function
  - `bytes` - All paths
  - `digest` - Covers path with `id=1`
  - `i` - Covers path with `id=1`
- Testing the function `getPKCS5Sha256Hash` tests the following paths for each variable:
  - `text` - All paths
  - `iteration` - Path with `id=2`
  - `md` - Covers the path `id=3` (redundant) and `id=4`
  - `bytes` - All paths
  - `digest` - All paths except path with `id=1`
  - `i` - All paths except path with `id=1`

## jpass.util.StringUtils.stripString

![Dataflow](assets/StripStringDataflow.png)

### Variable `text`
| **var** | **id** | **def** | **use** | **path**  |
| :-----: | :----: | :-----: | :-----: | :-------: |
|  text   |   1    |    1    |    2    |   <1,2>   |
|  text   |   2    |    1    |    4    | <1,2,3,4> |
|  text   |   3    |    1    |  (3,T)  | <1,2,3,4> |
|  text   |   4    |    1    |  (3,F)  | <1,2,3,5> |

### Variable `length`

| **var** | **id** | **def** | **use** | **path**  |
| :-----: | :----: | :-----: | :-----: | :-------: |
| length  |   1    |    1    |    4    | <1,2,3,4> |
| length  |   2    |    1    |  (3,T)  | <1,2,3,4> |
| length  |   3    |    1    |  (3,F)  | <1,2,3,5> |

### Variable `result`

| **var** | **id** | **def** | **use** | **path** |
| :-----: | :----: | :-----: | :-----: | :------: |
| result  |   1    |    2    |    5    | <2,3,5>  |
| result  |   2    |    4    |    5    |  <4,5>   |


- **All-defs**
  - All-defs coverage is achieved as there is at least one def-clear path from every definition of `text`, `length`, and `result` to at least one c-use or p-use of each variable is covered: for example, for `text`, the path of pair id 1, for `length`, the path of pair id 1, and for `result`, the path of pair id 1.

- **All-c-uses**
  - `text`
    - It is only defined once and is c-used twice.
    - Pair ids 1 and 2 (one for each of the c-uses) imply the satisfaction of this testing criteria (one def-clear path from every definition to every c-use).
  - `length`
    - It is only defined and c-used once.
    - Pair id 1 implies the satisfaction of this testing criteria (one def-clear path from every definition to every c-use).
  - `result`
    - It is defined twice and c-used once.
    - Pair ids 1 and 2 (one for each of the definitions) imply the satisfaction of this testing criteria (one def-clear path from every definition to every c-use).

- **All-p-uses**
  - `text`
    - It is only defined once and is p-used twice.
    - Pair id 3 (or 4) imply the satisfaction of this testing criteria (one def-clear path from every definition to every p-use).
  - `length`
    - It is only defined once and is p-used twice. 
    - Pair id 2 (or 3) imply the satisfaction of this testing criteria (one def-clear path from every definition to every p-use).
  - `result`
    - Does not have any p-uses, therefore, there's no need to cover this criteria.

- **All-uses**
  - `text`
    - To satisfy this testing criteria, all paths are necessary.
  - `length`
    - To satisfy this testing criteria, all paths are necessary.
  - `result`
    - To satisfy this testing criteria, all paths are necessary.

### Implemented Tests

The tests already developed for the function already cover all paths needed to be tested. `testStripStringGreaterLength` and `testStripStringEqualLengths` are examples of two tests that together are sufficient to cover all paths.
- `testStripStringGreaterLength` tests the following paths for each of the variables:
  - `text` - All paths covered except the one with `id=4` (`id=1` redundant with the paths that `testStripStringEqualLengths` tests)
  - `length` - All paths covered except the one with `id=3`
  - `result` - Covers path with `id=2`
- `testStripStringEqualLengths` tests the following paths for each of the variables:
  - `text` - Covers the path `id=1` and `id=4` (`id=1` redundant with the paths that `testStripStringGreaterLength` tests)
  - `length` - Covers path with `id=3`
  - `result` - Covers path with `id=1`


## jpass.util.DateUtils.formatIsoDateTime

![Dataflow](assets/FormatIsoDateTimeDataflow.png)

### Variable `dateString`
|  **var**   | **id** | **def** | **use** |   **path**    |
| :--------: | :----: | :-----: | :-----: | :-----------: |
| dateString |   1    |    1    |    2    |     <1,2>     |
| dateString |   2    |    1    |    4    |   <1,2,3,4>   |
| dateString |   3    |    1    |    7    | <1,2,3,4,5,7> |

- **all-defs**
  - The path with `id=3` covers all the criteria needed
- **all-c-uses**
  - All paths are necessary to cover this criteria
- **all-p-uses**
  - Variable `dataString` doesn't have any p-use, therefore, there's no need to cover this criteria
- **all-uses**
  - All paths are necessary to cover this criteria

### Variable `formatter`
|  **var**  | **id** | **def** | **use** |       **path**       |
| :-------: | :----: | :-----: | :-----: | :------------------: |
| formatter |   1    |    1    |  (9,F)  |     <1,2,3,9,11>     |
| formatter |   2    |    1    |  (9,F)  |  <1,2,3,4,5,6,9,11>  |
| formatter |   3    |    1    |  (9,F)  | <1,2,3,4,5,7,8,9,11> |
| formatter |   4    |    1    |  (9,T)  |     <1,2,3,9,10>     |
| formatter |   5    |    1    |  (9,T)  |  <1,2,3,4,5,6,9,10>  |
| formatter |   6    |    1    |  (9,T)  | <1,2,3,4,5,7,8,9,10> |
| formatter |   7    |    1    |   10    |     <1,2,3,9,10>     |
| formatter |   8    |    1    |   10    |  <1,2,3,4,5,6,9,10>  |
| formatter |   9    |    1    |   10    | <1,2,3,4,5,7,8,9,10> |
| formatter |   10   |    1    |   11    |     <1,2,3,9,11>     |
| formatter |   11   |    1    |   11    |  <1,2,3,4,5,6,9,11>  |
| formatter |   12   |    1    |   11    | <1,2,3,4,5,7,8,9,11> |

- **all-defs**
  - The path with `id=3` covers all the criteria needed, rest of the paths is redudant
- **all-c-uses**
  - The paths with `id=9`, `id=12` covers all the criteria needed, rest of the paths is redudant or not appliable
- **all-p-uses**
  - The paths with `id=3`, `id=6` covers all the criteria needed, rest of the paths is redudant or not appliable
- **all-uses**
  - The paths mentioned above cover all the criteria needed, as the other paths are redundant


### Variable `dateTime`
| **var**  | **id** | **def** | **use** |   **path**   |
| :------: | :----: | :-----: | :-----: | :----------: |
| dateTime |   1    |    2    |   10    | <1,2,3,9,10> |
| dateTime |   2    |    2    |   11    | <1,2,3,9,11> |
| dateTime |   3    |    6    |   10    |   <6,9,10>   |
| dateTime |   4    |    6    |   11    |   <6,9,11>   |
| dateTime |   5    |    8    |   10    |   <8,9,10>   |
| dateTime |   6    |    8    |   11    |   <8,9,11>   |

- **all-defs**
  - The paths with `id=1`, `id=3` and `id=5` covers all the criteria needed, rest of the paths is redudant
- **all-c-uses**
  - All paths are necessary to cover this criteria
- **all-p-uses**
  - Variable `dateTime` doesn't have any p-use, therefore, there's no need to cover this criteria
- **all-uses**
  - All paths are necessary to cover this criteria


### Variable `e`
| **var** | **id** | **def** | **use** | **path** |
| :-----: | :----: | :-----: | :-----: | :------: |
|    e    |   1    |    3    |  (3,F)  |  <3,9>   |
|    e    |   2    |    3    |  (3,T)  |  <3,4>   |

Variable `e` refers to an exception. To represent these paths, we opted to represent following closer to how it works in practice (Java), e.g. the variable is first defined and then used in the same line.

- **all-defs**
  - The paths with `id=1` covers all the criteria needed
- **all-c-uses**
  - Variable `e` doesn't have any c-use, therefore, there's no need to cover this criteria
- **all-p-uses**
  - All paths are necessary to cover this criteria
- **all-uses**
  - All paths are necessary to cover this criteria

### Variable `date`
| **var** | **id** | **def** | **use** | **path** |
| :-----: | :----: | :-----: | :-----: | :------: |
|  date   |   1    |    4    |    6    | <4,5,6>  |

- **all-defs**
  - All paths are necessary to cover this criteria
- **all-c-uses**
  - All paths are necessary to cover this criteria
- **all-p-uses**
  - Variable `date` doesn't have any c-use, therefore, there's no need to cover this criteria
- **all-uses**
  - All paths are necessary to cover this criteria

### Variable `ex`
| **var** | **id** | **def** | **use** | **path** |
| :-----: | :----: | :-----: | :-----: | :------: |
|   ex    |   1    |    5    |  (5,F)  |  <5,6>   |
|   ex    |   2    |    5    |  (5,T)  |  <5,7>   |

Variable `ex` refers to an exception. To represent these paths, we opted to represent following closer to how it works in practice (Java), e.g. the variable is first defined and then used in the same line.

- **all-defs**
  - The paths with `id=1` covers all the criteria needed
- **all-c-uses**
  - Variable `ex` doesn't have any c-use, therefore, there's no need to cover this criteria
- **all-p-uses**
  - All paths are necessary to cover this criteria
- **all-uses**
  - All paths are necessary to cover this criteria

### Implemented Tests
The implemented tests in previous assignments already covered all paths that are possible to test
- `testNullFormatterFormatIsoDateTime` test:
  - `dateString` - Covers path `id=1`
  - `formatter` - Path with `id=6` and `id=9`
  - `dateTime` - Path wtih `id=1`
  - `e` - Path with `id=1`
  - `date` - Does not cover
  - `ex` - Does not cover
- `testValidArgumentsFormatIsoDateTime` test:
  - `dateString` - Covers path `id=1`
  - `formatter` - Path with `id=3` and `id=12`
  - `dateTime` - Path with `id=2`
  - `e` - Path with `id=1`
  - `date` - Does not cover
  - `ex` - Does not cover
- `testNullDateStringFormatIsoDateTime` test:
  - `dateString` - Covers path `id=1`, `id=2` and `id=3`
  - `formatter` - Path with `id=3` and `id=12`
  - `dateTime` - Covers path with `id=6`
  - `e` - Covers path with `id=2`
  - `date` - Covers path with `id=1`
  - `ex` - Covers path with `id=2`
- `testNullDateStringFormatIsoDateTime2` test:
  - `dateString` - Covers path `id=1`, `id=2` and `id=3`
  - `formatter` - Path with `id=3` and `id=12`
  - `dateTime` - Covers path with `id=5`
  - `e` - Covers path with `id=2`
  - `date` - Covers path with `id=1`
  - `ex` - Covers path with `id=2`
- `testFormatIsoDateTimeUnixTimestamp` test:
  - `dateString` - Covers path `id=1` and `id=2`
  - `formatter` - Path with `id=3` and `id=12`
  - `dateTime` - Covers path with `id=4`
  - `e` - Covers path with `id=2`
  - `date` - Covers path with `id=1`  
  - `ex` - Covers path with `id=1`
- `testFormatIsoDateTimeUnixTimestamp2` test:
  - `dateString` - Covers path `id=1` and `id=2`
  - `formatter` - Path with `id=3` and `id=12`
  - `dateTime` - Covers path with `id=3`
  - `e` - Covers path with `id=2`
  - `date` - Covers path with `id=1`  
  - `ex` - Covers path with `id=1`