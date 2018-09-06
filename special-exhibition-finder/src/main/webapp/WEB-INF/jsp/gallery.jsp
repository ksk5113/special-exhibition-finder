<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE HTML>
<!--
	Snapshot by TEMPLATED
	templated.co @templatedco
	Released for free under the Creative Commons Attribution 3.0 license (templated.co/license)
-->
<html>
	<head>
		<title>Special Exhibition Finder</title>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<link rel="stylesheet" type="text/css" href="/css/main.css" />
	</head>
	<body>
		<div class="page-wrap">

			<!-- Nav -->
				<nav id="nav">
					<ul>
						<li><a href="/index"><span class="icon fa-home"></span></a></li>
						<li><a href="/gallery" class="active"><span class="icon fa-camera-retro"></span></a></li>
						<li><a href="/info"><span class="icon fa-info-circle"></span></a></li>
					</ul>
				</nav>

			<!-- Main -->
				<section id="main">
					<!-- Header -->
						<header id="header">
							<div class="index_header">
								최근 업데이트 :
								<span>${updated}</span>
							</div>
						</header>

					<!-- Gallery -->
						<section id="galleries">

							<!-- Photo Galleries -->
								<div class="gallery">

									<!-- Filters -->
										<header>
											<h1>진행 중인 전시</h1>
											<ul class="tabs">
												<li><a href="#" data-tag="all" class="button active">전체</a></li>
												<li><a href="#" data-tag="seoul" class="button">서울</a></li>
												<li><a href="#" data-tag="gyeonggi" class="button">경기권</a></li>
												<li><a href="#" data-tag="gangwon" class="button">강원권</a></li>
												<li><a href="#" data-tag="chungcheong" class="button">충청권</a></li>
												<li><a href="#" data-tag="yeongnam" class="button">영남권</a></li>
												<li><a href="#" data-tag="honam" class="button">호남권</a></li>
												<li><a href="#" data-tag="jeju" class="button">제주도</a></li>
											</ul>
										</header>

										<div class="content">
											<c:forEach items="${exhibitionList}" var="ex">
												<div class="media all ${ex.museum.location}">
													<a href="/generic?exhibitionName=${ex.name}">
														<img src="${ex.image}" alt="" title="${ex.name}" />
														<div class="image_name">
															<b>${ex.name}</b>
														</div>
														<div class="image_museum">
															${ex.museum.name}
															</br>
															${ex.getCalculatedRemainingDays()} 종료
														</div>
													</a>
												</div>
	          								</c:forEach>
										</div>
								</div>
						</section>

					<!-- Footer -->
						<footer id="footer">
							<div class="copyright">
								&copy; Untitled Design: <a href="https://templated.co/">TEMPLATED</a>. Images: <a href="https://unsplash.com/">Unsplash</a>.
							</div>
						</footer>
				</section>
		</div>

		<!-- Scripts -->
			<script src="/js/jquery.min.js"></script>
			<script src="/js/jquery.poptrox.min.js"></script>
			<script src="/js/jquery.scrolly.min.js"></script>
			<script src="/js/skel.min.js"></script>
			<script src="/js/util.js"></script>
			<script src="/js/main.js"></script>

	</body>
</html>