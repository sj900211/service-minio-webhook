# Minio Webhook
>> ``` yaml
>> freshr:
>>   thumbnail:
>>     directory: string
>>     list:
>>       -
>>         prefix: string
>>         suffix: string
>>         width: number
>>         height: number
>>         position: Enum
>> ```
>> ## thumbnail
>>> 썸네일 설정
>> ## directory
>>> 썸네일 저장 최상위 디렉토리
>>> 선택 항목. 작성하지 않으면 thumbnail 로 설정됨
>> ## list
>>> 썸네일 설정 목록  
>>> 필수 항목: 하나라도 등록 해야 한다.
>>> ### prefix & suffix
>>>> 썸네일 파일 이름 접두어 와 접미어  
>>>> 선택 항목  
>>>> 값을 입력한다면 다른 썸네일 설정과 중복되지 않도록 작성해야 한다.  
>>>> 값을 입력하지 않는다면 자동으로 `width 값`x`height 값` 이 suffix 로 설정된다.  
>>>> **EX)** filename100x100.png
>>> ### width & height
>>>> 선택 항목  
>>>> 하지만 둘 중 하나는 필수로 작성해야 한다.  
>>>> 둘 중 하나만 입력하면 다른 하나의 값으로 자동 설정된다.  
>>>> 기본적으로 중복 설정을 허용하지만     
>>>> prefix, suffix 설정을 하지 않았다면 중복을 허용하지 않는다.  
>>>> 단위는 px 이다.
>>> ### position
>>>> 선택 항목  
>>>> Resize 는 원본 이미지 비율에 맞도록 설정된다.    
>>>> 비율때문에 조절된 이미지의 가로나 세로 사이즈가 설정한 값보다 크다면  
>>>> 이미지는 설정한 사이즈대로 만들기 위해서 이미지를 자른다.  
>>>> 이때 자르기 위한 이미지의 기준을 설정하는 항목
>>>> 설정 가능한 값은 다음과 같다.
>>>> **WARNING:** 아래 목록과 같이 UPPER_UNDERSCORE 로 작성해야한다.
>>>> - TOP_LEFT
>>>>> 이미지의 상단 왼쪽을 기준으로 초과된 영역을 제거
>>>> - TOP_CENTER
>>>>> 이미지의 상단 가운데을 기준으로 초과된 영역을 제거
>>>> - TOP_RIGHT
>>>>> 이미지의 상단 오른쪽을 기준으로 초과된 영역을 제거
>>>> - CENTER_LEFT
>>>>> 이미지의 중단 왼쪽을 기준으로 초과된 영역을 제거
>>>> - CENTER
>>>>> 이미지의 중단 가운데을 기준으로 초과된 영역을 제거  
>>>>> 값을 작성하지 않았다면 자동으로 CENTER 가 선택된다.
>>>> - CENTER_RIGHT
>>>>> 이미지의 중단 오른쪽을 기준으로 초과된 영역을 제거
>>>> - BOTTOM_LEFT
>>>>> 이미지의 하단 왼쪽을 기준으로 초과된 영역을 제거
>>>> - BOTTOM_CENTER
>>>>> 이미지의 하단 가운데을 기준으로 초과된 영역을 제거
>>>> - BOTTOM_RIGHT
>>>>> 이미지의 하단 오른쪽을 기준으로 초과된 영역을 제거
>>>> EX)
>>>> ```yaml
>>>> freshr:
>>>>   thumbnail-list:
>>>>     -
>>>>       width: 100
>>>>       height: 100
>>>>       position: CENTER
>>>> ```
>>>> 위와 같이 작성했을 때 이미지 사이즈가 100x100 을 초과하면  
>>>> 정가운데를 중심으로 50px 씩 사각형 모양으로 남기고 나머지는 자르고 저장한다.